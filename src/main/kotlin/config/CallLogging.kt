package s.knyazev.example.config

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.DEBUG
        format { call ->
            val path = call.request.path()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]

            "Path: $path, HTTPMethod: $httpMethod, UserAgent: $userAgent"
        }
    }
}
