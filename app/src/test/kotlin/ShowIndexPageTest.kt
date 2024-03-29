package be.alpago.website

import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.Photoswipe
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.interfaces.ktor.animals
import be.alpago.website.interfaces.ktor.articles
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.clear
import be.alpago.website.interfaces.ktor.firestore
import be.alpago.website.interfaces.ktor.highlights
import be.alpago.website.interfaces.ktor.imageMetadata
import be.alpago.website.interfaces.ktor.index
import be.alpago.website.interfaces.ktor.inject
import be.alpago.website.interfaces.ktor.mock
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.templates
import be.alpago.website.interfaces.ktor.validation
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.jsoup.Jsoup
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

private const val PAGE_URL = "/index.html"

class ShowIndexPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private fun indexPageTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
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

                articles()
                highlights()
                imageMetadata()

                animals()
                index()
            }

            deleteAll()

            block()
        }
    }

    private suspend fun ApplicationTestBuilder.deleteAll() {
        delete("/api/index/article")
        delete("/api/index/news")
        delete("/api/index/guilds")
        delete("/api/index/trainings")
    }

    @Test
    fun `the index page is initially empty`() = indexPageTestApplication {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[data-test-id=article]").shouldBeEmpty()
        document.select("[data-test-id=news] [data-test-id=news-highlight]").shouldBeEmpty()
        document.select("[data-test-id=trainings] [data-test-id=trainings-photo]").shouldBeEmpty()
        document.select("[data-test-id=guilds] [data-test-id=guilds-highlight]").shouldBeEmpty()
    }

    @Test
    fun `the main article can be created`() = indexPageTestApplication {
        articleTest(
            articleUrl = "/api/index/article",
            pageUrl = PAGE_URL,
            sectionId = "article",
        )
    }

    @Test
    fun `news highlights can be created`() = indexPageTestApplication {
        highlightTest(
            highlightUrl = "/api/index/news",
            pageUrl = PAGE_URL,
            sectionId = "news",
        )
    }

    @Test
    fun `training photos can be created`() = indexPageTestApplication {
        photoGalleryTest(
            galleryUrl = "/api/index/trainings",
            pageUrl = PAGE_URL,
            sectionId = "trainings",
        )
    }

    @Test
    fun `guild highlights can be created`() = indexPageTestApplication {
        highlightTest(
            highlightUrl = "/api/index/guilds",
            pageUrl = PAGE_URL,
            sectionId = "guilds",
        )
    }
}
