package io.github.petercrawley.livemap.api.objects

import io.github.petercrawley.livemap.api.collections.Regions
import org.bukkit.World

interface LiveMapWorld {
	/**
	 * World represented by this object.
	 * @see World
	 */
	val world: World

	/**
	 * Queues a block update, update is not guaranteed to happen immediately.
	 * When doing large amounts of updates within a region it is more efficient to call functions on the region.
	 * @param blockX X coordinate of the block within the world.
	 * @param blockZ Z coordinate of the block within the world.
	 * @see LiveMapRegion.queueBlockUpdate
	 */
	fun queueBlockUpdate(blockX: Int, blockZ: Int) {
		val regionX = blockX.floorDiv(512)
		val regionZ = blockZ.floorDiv(512)

		regions[regionX, regionZ].queueBlockUpdate((blockX - regionX * 512).toShort(), (blockZ - regionZ * 512).toShort())
	}

	val regions: Regions
}