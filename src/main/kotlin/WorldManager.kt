package io.github.petercrawley.livemap

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldLoadEvent
import org.bukkit.event.world.WorldUnloadEvent

internal class WorldManager : Listener {
	private val worlds = mutableSetOf<LiveMapWorld>()

	@EventHandler
	fun onWorldLoad(event: WorldLoadEvent) {
		worlds.add(LiveMapWorld(event.world))
	}

	@EventHandler
	fun onWorldUnload(event: WorldUnloadEvent) {
		worlds.remove(LiveMapWorld(event.world))
	}
}