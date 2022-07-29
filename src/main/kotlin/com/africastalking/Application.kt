package com.africastalking

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.africastalking.route.*
import io.ktor.server.application.*

fun main() {
  //  embeddedServer(Netty, port = 8088, host = "192.168.31.23") {
    embeddedServer(Netty,port = System.getenv("PORT").toInt()) {
        log.info("Ktor server enter at services")
        configureSerialization()

        configureRouting()
    }.start(wait = true)
}
