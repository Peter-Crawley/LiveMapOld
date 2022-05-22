package io.github.petercrawley.livemap.api

import org.jetbrains.annotations.ApiStatus.Internal

object LiveMapAPIProvider {
	private var actualLiveMap: LiveMapAPI? = null

	/**
	 * @see LiveMapAPI
	 */
	var liveMapAPI: LiveMapAPI
		get() = actualLiveMap ?: throw NoClassDefFoundError("No implementation of LiveMapAPI is loaded.")
		@Internal set(value) {
			actualLiveMap = value
		}
}