package io.github.petercrawley.livemap.api.collections

import io.github.petercrawley.livemap.api.objects.LiveMapWorld
import org.bukkit.World

interface Worlds{
	/**
	 * @see LiveMapWorld
	 */
	operator fun get(world: World): LiveMapWorld
}