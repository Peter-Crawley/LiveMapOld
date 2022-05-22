package io.github.petercrawley.livemap.api

import io.github.petercrawley.livemap.api.collections.Worlds

interface LiveMapAPI {
	/**
	 * @see Worlds
	 */
	val worlds: Worlds
}