package io.github.petercrawley.livemap.api.extensions

import io.github.petercrawley.livemap.api.LiveMapAPIProvider
import io.github.petercrawley.livemap.api.collections.Regions
import org.bukkit.World

fun World.queueLiveMapBlockUpdate(blockX: Int, blockZ: Int) =
	LiveMapAPIProvider.liveMapAPI.worlds[this].queueBlockUpdate(blockX, blockZ)

val World.liveMapRegions: Regions
	get() = LiveMapAPIProvider.liveMapAPI.worlds[this].regions