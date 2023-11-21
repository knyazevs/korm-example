package s.knyazev.example

import io.ktor.server.application.*
import s.knyazev.example.product.ProductService
import s.knyazev.example.product.productRouting

fun Application.configureRouting(productService: ProductService) {
    this.productRouting(productService)
}
