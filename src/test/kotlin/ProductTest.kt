import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.uuid.UUID
import kotlinx.uuid.generateUUID
import org.flywaydb.core.Flyway
import org.testcontainers.containers.PostgreSQLContainer
import s.knyazev.example.config.configureLogging
import s.knyazev.example.config.configureSerialization
import s.knyazev.example.configureRouting
import s.knyazev.example.product.ProductDTO
import s.knyazev.example.product.ProductService
import java.lang.System.setProperty
import kotlin.test.Test


class ProductTest {
    private var postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15-alpine")

    @Test
    fun testRoot() = testApplication {
        configApp()

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val payload = JsonObject(
            mapOf(
                "publicKey" to JsonPrimitive("publicKey"),
                "privateKey" to JsonPrimitive("privateKey")
            )
        )
        val product = ProductDTO(UUID.generateUUID(), 100, payload)

        val createResponse = client.put("/create") {
            contentType(ContentType.Application.Json)
            setBody(product)
        }
        val getResponse = client.get("/")
        assertEquals(HttpStatusCode.OK, getResponse.status)
        println(getResponse.bodyAsText())
        val productFromServer: List<ProductDTO> = getResponse.body()
        assertEquals(listOf(product), productFromServer)
    }.also {
        stop()
    }

    private fun TestApplicationBuilder.configApp() {
        postgresContainer.start()
        setProperty("POSTGRES_HOST", postgresContainer.host)
        setProperty("POSTGRES_PORT", postgresContainer.firstMappedPort.toString())
        setProperty("POSTGRES_DATABASE", postgresContainer.databaseName)
        setProperty("POSTGRES_USER", postgresContainer.username)
        setProperty("POSTGRES_PASSWORD", postgresContainer.password)

        migrate()

        application {
            val productService = ProductService()

            configureRouting(productService)
            configureSerialization()
            configureLogging()
        }
    }

    private fun stop() {
        postgresContainer.stop()
    }

    private fun migrate() {
        val jdbcUrl = postgresContainer.getJdbcUrl()

        val username = postgresContainer.username
        val password = postgresContainer.password
        val flyway = Flyway.configure().dataSource(jdbcUrl, username, password).load()
        flyway.migrate()
        println("Migration done")
    }
}
