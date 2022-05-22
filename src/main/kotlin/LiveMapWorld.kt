package io.github.petercrawley.livemap

import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.world.ChunkLoadEvent

internal class LiveMapWorld(
	internal val bukkit: World,
	plugin: LiveMap
) : LiveMapListener(plugin) {
	private val loadedRegions = mutableMapOf<Int, LiveMapRegion>()

	@EventHandler(priority = EventPriority.MONITOR)
	fun onChunkLoad(event: ChunkLoadEvent) {
		if (event.world != bukkit) return

		val regionKey = event.chunk.x.floorDiv(32).shl(16) + event.chunk.z.floorDiv(32)

		val region = loadedRegions.getOrPut(regionKey) {
			LiveMapRegion(this, event.chunk.x.toShort(), event.chunk.z.toShort())
		}

		val chunkSnapshot = event.chunk.getChunkSnapshot(true, false, false)

		for (x in 0 .. 15) for (z in 0 .. 15) {
			val highestY = chunkSnapshot.getHighestBlockYAt(x, z)

			if (highestY > event.world.minHeight && highestY < event.world.maxHeight)

			region[x.toShort(), z.toShort()] = chunkSnapshot.getBlockType(x, highestY, z).ordinal.toShort() // TODO: Material ordinal values should not be considered reliable
		}
	}
}