import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class MyEntity(
    val name: String,
)

@Serializable
data class MyRequest<T> (
    val id: String,
    val data: T,
)

@Serializable
data class MyEntityRequest (
    val id: String,
    val data: MyEntity
)

@Serializable
data class MyResponse<T> (
    val id: String,
    val data: T,
)

@Serializable
data class PostmanResponse<T> (
    val data: MyResponse<T>,
)


fun main() {
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
    }

    // Working sample
    print("Running working test...")
    runBlocking {
        val request = MyEntityRequest(
            "test",
            MyEntity("Hello World")
        )
        val response = client.post<PostmanResponse<MyEntity>>("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            body = request
        }
        assert(response.data.id == "test") { "Expected id to be \"test\""}
        assert(response.data.data.name == "Hello World") { "Expected data.name to be \"Hello World\""}
    }
    println("successful")

    // Not working sample
    print("Running broking test...")
    runBlocking {
        val request = MyRequest(
            "test",
            MyEntity("Hello World")
        )
        val response = client.post<PostmanResponse<MyEntity>>("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            body = request
        }
        assert(response.data.id == "test") { "Expected id to be \"test\""}
        assert(response.data.data.name == "Hello World") { "Expected data.name to be \"Hello World\""}
    }
    println("successful")
}
