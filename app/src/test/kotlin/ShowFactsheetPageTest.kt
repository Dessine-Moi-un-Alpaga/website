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

private const val PAGE_URL = "/factsheets.html"

class ShowFactsheetPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private fun factsheetPageTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
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
        delete("/api/factsheets/article")
        delete("/api/factsheets/factsheets")
    }

    @Test
    fun `the factsheet page is initially empty`() = factsheetPageTestApplication {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[data-test-id=article]").shouldBeEmpty()
        document.select("[data-test-id=highlights] [data-test-id=highlights-highlight]").shouldBeEmpty()
    }

    @Test
    fun `the main article can be created`() = factsheetPageTestApplication {
        articleTest(
            articleUrl = "/api/factsheets/article",
            pageUrl = PAGE_URL,
            sectionId = "article",
        )
    }

    @Test
    fun `factsheets can be created`() = factsheetPageTestApplication {
        highlightTest(
            highlightUrl = "/api/factsheets/factsheets",
            pageUrl = PAGE_URL,
            sectionId = "highlights",
        )
    }
}
