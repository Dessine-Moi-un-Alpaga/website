package be.alpago.website.e2e

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

private const val PAGE_URL = "/factsheets.html"

class FactsheetPageTest {

    @Test
    fun `the factsheet page is initially empty`() = endToEndTest {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[data-test-id=article]").shouldBeEmpty()
        document.select("[data-test-id=highlights] [data-test-id=highlights-highlight]").shouldBeEmpty()
    }

    @Test
    fun `the main article can be created`() = endToEndTest { templateProperties ->
        articleTest(
            articleUrl = "/api/factsheets/article",
            baseAssetUrl = templateProperties.baseAssetUrl,
            pageUrl = PAGE_URL,
            sectionId = "article",
        )
    }

    @Test
    fun `factsheets can be created`() = endToEndTest { templateProperties ->
        highlightTest(
            baseAssetUrl = templateProperties.baseAssetUrl,
            highlightUrl = "/api/factsheets/factsheets",
            pageUrl = PAGE_URL,
            sectionId = "highlights",
        )
    }
}
