package io.github.petercrawley.livemap

import java.io.File
import java.io.RandomAccessFile
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

internal class LiveMapRegion(world: LiveMapWorld, regionX: Short, regionZ: Short) {
	private val fileChannel: MappedByteBuffer =
			RandomAccessFile(File(world.bukkit.name)
					.resolve("$regionX-$regionZ.lmr"), "rwd")
					.channel.map(FileChannel.MapMode.READ_WRITE, 0, 524288)

	internal operator fun set(x: Short, z: Short, value: Short) =
			fileChannel.putShort(x * 1024 + z * 2, value)

	internal operator fun get(x: Short, z: Short) =
			fileChannel.getShort(x * 1024 + z * 2)
}