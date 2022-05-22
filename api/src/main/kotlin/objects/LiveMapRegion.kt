package io.github.petercrawley.livemap.api.objects

interface LiveMapRegion {
	/**
	 * X coordinate of the region. Lines up with Minecraft Regions.
	 */
	val regionX: Int

	/**
	 * X coordinate of the region. Lines up with Minecraft Regions.
	 */
	val regionZ: Int

	/**
	 * Queues a block update, update is not guaranteed to happen immediately.
	 * @param blockX X coordinate of the block within the region.
	 * @param blockZ Z coordinate of the block within the region.
	 * @see LiveMapWorld.queueBlockUpdate
	 */
	fun queueBlockUpdate(blockX: Short, blockZ: Short)
}
