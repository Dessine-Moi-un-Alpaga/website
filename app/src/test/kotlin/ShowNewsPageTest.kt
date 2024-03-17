package be.alpago.website

import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.interfaces.ktor.animals
import be.alpago.website.interfaces.ktor.articles
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.clear
import be.alpago.website.interfaces.ktor.firestore
import be.alpago.website.interfaces.ktor.mock
import be.alpago.website.interfaces.ktor.news
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

private const val PAGE_URL = "/news.html"

class ShowNewsPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private fun newsPageTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
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

                animals()
                news()
            }

            deleteAll()

            block()
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
    fun `news articles can be created`() = newsPageTestApplication {
        articleTest(
            articleUrl = "/api/news/articles",
            pageUrl = PAGE_URL,
            sectionId = "news-0"
        )
    }
}
