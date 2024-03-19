package be.alpago.website

import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.interfaces.ktor.animals
import be.alpago.website.interfaces.ktor.articles
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.clear
import be.alpago.website.interfaces.ktor.fiberAnalyses
import be.alpago.website.interfaces.ktor.firestore
import be.alpago.website.interfaces.ktor.highlights
import be.alpago.website.interfaces.ktor.imageMetadata
import be.alpago.website.interfaces.ktor.index
import be.alpago.website.interfaces.ktor.mock
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.templates
import be.alpago.website.interfaces.ktor.validation
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.jsoup.Jsoup
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
                authentication()

                mock<AuthenticationProperties> {
                    AuthenticationProperties(credentials = CREDENTIALS)
                }

                validation()
                i18n()
                serialization()

                firestore()
                templates()

                animals()
                fiberAnalyses()
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
