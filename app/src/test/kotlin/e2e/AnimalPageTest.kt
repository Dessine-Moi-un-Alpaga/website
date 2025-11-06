package be.alpago.website.e2e

import be.alpago.website.domain.Animal
import be.alpago.website.domain.FiberAnalysis
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.util.UUID


class AnimalPageTest {

    @Test
    fun `the animal page should initially not exist`() = endToEndTest {
        val id = "${UUID.randomUUID()}"
        val response = client.get("/animals/$id")
        response shouldHaveStatus HttpStatusCode.NotFound
    }

    @Test
    fun `an animal can be created`() = endToEndTest {
        val jsonClient = createJsonClient()

        val animal: Animal = FAKER.randomProvider.randomClassInstance()

        var response = jsonClient.put("/api/animals") {
            contentType(ContentType.Application.Json)
            authenticate()
            setBody(Json.encodeToString(animal))
        }
        response shouldHaveStatus HttpStatusCode.OK

        val fiberAnalysis: FiberAnalysis = FAKER.randomProvider.randomClassInstance {
            namedParameterGenerator("id") {
                animal.id
            }
        }

        response = jsonClient.put("/api/fiberAnalyses") {
            contentType(ContentType.Application.Json)
            authenticate()
            setBody(Json.encodeToString(fiberAnalysis))
        }
        response shouldHaveStatus HttpStatusCode.OK

        response = client.get("/animals/${animal.id}.html")
        response shouldHaveStatus HttpStatusCode.OK
    }
}
