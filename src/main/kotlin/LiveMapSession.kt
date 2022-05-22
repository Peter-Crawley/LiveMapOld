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

  @OnOpen
  @Suppress("unused") // Entrypoint
  fun onWebSocketConnect(session: Session) {
	  this.session = session

    session.sendWorldsMessage()
  }

  @OnMessage
  @Suppress("unused") // Entrypoint
  fun onBinaryMessage(session: Session, message: ByteBuffer) {
	  when (message.get().toInt()) {
			0 -> return
      1 -> session.sendKeepAliveMessage()
		  2 -> {
				println("Region Requested: ${message.short} ${message.short}")
			}
      else -> session.closeInvalid()
    }
  }

  private fun Session.closeInvalid() {
    close(CloseReason(CloseCodes.PROTOCOL_ERROR, "Invalid message type."))
  }

  private fun Session.sendWorldsMessage() {
    val worlds = arrayOf(
      "world",
	    "world_nether",
	    "world_the_end"
    )

    var worldsArraySize = 2 + worlds.size
    worlds.forEach {
      worldsArraySize += it.length
    }

    val byteBuffer = ByteBuffer.allocate(worldsArraySize)

    byteBuffer.put(0x00)

    byteBuffer.put(worlds.size.toByte())

    worlds.forEach {
      byteBuffer.put(it.length.toByte())
      byteBuffer.put(it.toByteArray())
    }

    byteBuffer.position(0)

    basicRemote.sendBinary(byteBuffer, true)
  }

  private fun Session.sendKeepAliveMessage() {
    basicRemote.sendBinary(ByteBuffer.wrap(byteArrayOf(1)), true)
  }
}