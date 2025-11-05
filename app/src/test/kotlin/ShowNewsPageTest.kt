package be.alpago.website

import be.alpago.website.adapters.adapters
import be.alpago.website.application.queries.queries
import be.alpago.website.interfaces.interfaces
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.libs.di.clear
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.jsoup.Jsoup
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val PAGE_URL = "/news.html"

class ShowNewsPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private fun newsPageTestApplication(block: suspend ApplicationTestBuilder.(TemplateProperties) -> Unit) {
        var templateProperties: TemplateProperties? = null

        testApplication {
            application {
                dependencies.provide {
                    AuthenticationProperties(credentials = CREDENTIALS)
                }

                adapters()
                queries()
                interfaces()

                templateProperties = dependencies.resolve<TemplateProperties>()
            }

            deleteAll()

            block(templateProperties!!)
        }
    }

    private suspend fun ApplicationTestBuilder.deleteAll() {
        delete("/api/news/articles")
    }

    @Test
    fun `the news page is initially empty`() = newsPageTestApplication {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[data-test-id^=news-]").shouldBeEmpty()
    }

    @Test
    fun `news articles can be created`() = newsPageTestApplication { templateProperties ->
        articleTest(
            articleUrl = "/api/news/articles",
            pageUrl = PAGE_URL,
            sectionId = "news-0",
            templateProperties,
        )
    }
}
