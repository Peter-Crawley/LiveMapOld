package io.github.petercrawley.livemap

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
import org.bstats.charts.SingleLineChart
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldLoadEvent
import org.bukkit.event.world.WorldUnloadEvent
import org.bukkit.plugin.java.JavaPlugin
import org.eclipse.jetty.server.HttpConfiguration
import org.eclipse.jetty.server.HttpConnectionFactory
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer
import java.io.File

@Suppress("unused") // Entrypoint
class LiveMap : JavaPlugin(), Listener {
	private val webServer = Server()

	private val connector: ServerConnector

	init {
		val httpConfiguration = HttpConfiguration()
		httpConfiguration.sendServerVersion = false
		httpConfiguration.sendDateHeader = false

		connector = ServerConnector(webServer, HttpConnectionFactory(httpConfiguration))

		webServer.addConnector(connector)

		val servletHandler = ServletContextHandler(webServer, "/")

		val liveMapServlet = ServletHolder(object : HttpServlet() {
			override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
				response.writer.println(
					File("../src/main/resources/the.html")
						.readText()
						.replace("\$url", "localhost:8080")
				)
			}
		})

		servletHandler.addServlet(liveMapServlet, "/")

		webServer.handler = servletHandler

		JakartaWebSocketServletContainerInitializer.configure(servletHandler) { _, websocketContainer ->
			websocketContainer.addEndpoint(LiveMapSession::class.java)
		}

		connector.port = 8080
		webServer.start()
	}

	private val worlds = mutableMapOf<World, LiveMapWorld>()

	override fun onEnable() {
		Metrics(this, 15261).apply {
			addCustomChart(SingleLineChart("viewers") { 0 })
			addCustomChart(SimplePie("worlds") { worlds.size.toString() })
		}

		server.pluginManager.registerEvents(this, this)
	}

	@EventHandler(priority = EventPriority.MONITOR)
	fun onWorldLoad(event: WorldLoadEvent) {
		worlds[event.world] = LiveMapWorld(event.world, this)
	}

	@EventHandler(priority = EventPriority.MONITOR)
	fun onWorldUnload(event: WorldUnloadEvent) {
		worlds.remove(event.world)
	}
}