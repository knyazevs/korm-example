package s.knyazev.example.product

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.uuid.UUID
import kotlinx.uuid.generateUUID

fun Application.productRouting(productService: ProductService) {
    routing {
        get("/") {
            val resultDTO = productService.get().map { it.toDto() }
            call.respond(resultDTO)
        }

        put("/create") {
            val createDto = call.receive<ProductDTO>()
            val create = createDto.toDomain().apply {
                id = this.id ?: UUID.generateUUID()
            }
            val resultDTO = productService.create(create).toDto()
            call.respond(resultDTO)
        }

        post("/update") {
            val productId = UUID(call.parameters["id"].orEmpty())
            val createDto = call.receive<ProductDTO>()
            val update = createDto.toDomain()
            val resultDTO = productService.update(productId, update)?.toDto() ?: throw Exception("Product with id=$productId not found")
            call.respond(resultDTO)
        }

        delete("/delete") {
            val productId = UUID(call.parameters["id"].orEmpty())
            val result = productService.delete(productId)
            call.respond(result)
        }
    }
}
