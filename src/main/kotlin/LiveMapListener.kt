package io.github.petercrawley.livemap

import org.bukkit.event.Listener

abstract class LiveMapListener(plugin: LiveMap) : Listener {
	init {
		@Suppress("LeakingThis")
		plugin.server.pluginManager.registerEvents(this, plugin)
	}
}