package be.alpago.website

import be.alpago.website.adapters.adapters
import be.alpago.website.application.queries.queries
import be.alpago.website.interfaces.interfaces
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.libs.di.clear
import be.alpago.website.libs.di.mock
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

private const val PAGE_URL = "/index.html"

class ShowIndexPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private fun indexPageTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
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
