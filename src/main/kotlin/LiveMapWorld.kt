package io.github.petercrawley.livemap

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

internal class LiveMapWorld(
	val bukkit: World
) : Listener {
	@EventHandler
	fun onServerTickEnd(event: ServerTickEndEvent) {

	}
}