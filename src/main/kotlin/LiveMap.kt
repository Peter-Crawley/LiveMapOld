package io.github.petercrawley.livemap

import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
import org.bstats.charts.SingleLineChart
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused") // Plugin
class LiveMap : JavaPlugin() {
	override fun onEnable() {
		Metrics(this, 15261).apply {
			addCustomChart(SingleLineChart("viewers") { 0 })
			addCustomChart(SimplePie("worlds") { Bukkit.getWorlds().size.toString() })
		}

		server.pluginManager.registerEvents(WorldManager(), this)
	}
}