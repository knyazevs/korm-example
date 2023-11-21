package s.knyazev.example

import io.ktor.server.application.*
import s.knyazev.example.config.configureLogging
import s.knyazev.example.config.configureSerialization
import s.knyazev.example.product.ProductService


fun Application.module() {
    val productService = ProductService()

    configureRouting(productService)
    configureSerialization()
    configureLogging()
}