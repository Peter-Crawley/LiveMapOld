package io.github.petercrawley.livemap

import jakarta.websocket.CloseReason
import jakarta.websocket.CloseReason.CloseCodes
import jakarta.websocket.OnMessage
import jakarta.websocket.OnOpen
import jakarta.websocket.Session
import jakarta.websocket.server.ServerEndpoint
import java.nio.ByteBuffer

@ServerEndpoint(value = "/map")
class LiveMapSession {
	private lateinit var session: Session

	private var world = "world"

	private var viewingRegions = mutableSetOf<RegionCoordinates>()

	@OnOpen
	@Suppress("unused") // Entrypoint
	fun onWebSocketConnect(session: Session) {
		this.session = session

		sendWorldsList(listOf("world", "world_nether", "world_the_end"))
		sendPalette(listOf(0x00000000, 0x00ff0000))
	}

	fun sendWorldsList(worlds: Collection<String>) {
		val stream = session.basicRemote.sendStream

		stream.write(0x01)

		worlds.forEach { world ->
			stream.write(world.length)
			stream.write(world.toByteArray())
		}

		stream.close()
	}

	fun sendPalette(palette: Collection<Int>) {
		val byteBuffer = ByteBuffer.allocate(1 + (palette.size * 4))

		byteBuffer.put(0x02)

		palette.forEach { colour ->
			byteBuffer.putInt(colour)
		}

		byteBuffer.position(0)

		session.basicRemote.sendBinary(byteBuffer, true)
	}

	@OnMessage
	@Suppress("unused") // Entrypoint
	fun onBinaryMessage(session: Session, message: ByteBuffer) {
		try {
			when (message.get().toInt()) {
				0 -> session.basicRemote.sendBinary(ByteBuffer.allocate(1))
				1 -> {
					// TODO: Read world
				}

				3 -> {
					// TODO: Region Request
				}
			}
		} catch (_: Exception) {
			session.close(CloseReason(CloseCodes.PROTOCOL_ERROR, ""))
		}
	}
}