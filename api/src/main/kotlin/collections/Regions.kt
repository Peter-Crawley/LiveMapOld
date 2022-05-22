package io.github.petercrawley.livemap.api.collections

import io.github.petercrawley.livemap.api.objects.LiveMapRegion

interface Regions{
	/**
	 * @see LiveMapRegion
	 */
	operator fun get(x: Int, z: Int): LiveMapRegion
}