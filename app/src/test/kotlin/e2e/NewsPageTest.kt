package be.alpago.website.e2e

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

private const val PAGE_URL = "/news.html"

class NewsPageTest {

    @Test
    fun `the news page is initially empty`() = endToEndTest {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[data-test-id^=news-]").shouldBeEmpty()
    }

    @Test
    fun `news articles can be created`() = endToEndTest { templateProperties ->
        articleTest(
            articleUrl = "/api/news/articles",
            baseAssetUrl = templateProperties.baseAssetUrl,
            pageUrl = PAGE_URL,
            sectionId = "news-0",
        )
    }
}
