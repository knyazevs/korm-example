package s.knyazev.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlin.jvm.JvmStatic

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            server()
        }
    }
}

fun main() {
    Main.main(arrayOf())
}

fun server() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
