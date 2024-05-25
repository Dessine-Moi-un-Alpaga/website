package be.alpago.website

import be.alpago.website.adapters.adapters
import be.alpago.website.application.queries.queries
import be.alpago.website.interfaces.interfaces
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.libs.di.clear
import be.alpago.website.libs.di.mock
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID


class ShowAnimalPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private fun animalPageTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
        testApplication {
            application {
                adapters()
                queries()
                interfaces()

                mock<AuthenticationProperties> {
                    AuthenticationProperties(credentials = CREDENTIALS)
                }
            }

            deleteAll()

            block()
        }
    }

    private suspend fun ApplicationTestBuilder.deleteAll() {
        delete("/api/animals")
    }

//    @Test
//    fun `the anima page should initially not exist`() = animalPageTestApplication {
//        val id = "${UUID.randomUUID()}"
//        val response = client.get("/animals/$id")
//        response shouldHaveStatus HttpStatusCode.OK
//
//        val document = Jsoup.parse(response.bodyAsText())
//        document.select("[data-test-id=article]").shouldBeEmpty()
//        document.select("[data-test-id=news] [data-test-id=news-highlight]").shouldBeEmpty()
//        document.select("[data-test-id=trainings] [data-test-id=trainings-photo]").shouldBeEmpty()
//        document.select("[data-test-id=guilds] [data-test-id=guilds-highlight]").shouldBeEmpty()
//    }

    @Test
    fun `an animal can be created`() = animalPageTestApplication {
        val id = "${UUID.randomUUID()}"
        animalTest(id)
    }
}
