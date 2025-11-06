package be.alpago.website.e2e

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

private const val PAGE_URL = "/index.html"

class IndexPageTest {

    @Test
    fun `the index page is initially empty`() = endToEndTest {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[data-test-id=article]").shouldBeEmpty()
        document.select("[data-test-id=news] [data-test-id=news-highlight]").shouldBeEmpty()
        document.select("[data-test-id=trainings] [data-test-id=trainings-photo]").shouldBeEmpty()
        document.select("[data-test-id=guilds] [data-test-id=guilds-highlight]").shouldBeEmpty()
    }

    @Test
    fun `the main article can be created`() = endToEndTest { templateProperties ->
        articleTest(
            articleUrl = "/api/index/article",
            baseAssetUrl = templateProperties.baseAssetUrl,
            pageUrl = PAGE_URL,
            sectionId = "article",
        )
    }

    @Test
    fun `news highlights can be created`() = endToEndTest { templateProperties ->
        highlightTest(
            baseAssetUrl = templateProperties.baseAssetUrl,
            highlightUrl = "/api/index/news",
            pageUrl = PAGE_URL,
            sectionId = "news",
        )
    }

    @Test
    fun `training photos can be created`() = endToEndTest { templateProperties ->
        photoGalleryTest(
            baseAssetUrl = templateProperties.baseAssetUrl,
            galleryUrl = "/api/index/trainings",
            pageUrl = PAGE_URL,
            sectionId = "trainings",
        )
    }

    @Test
    fun `guild highlights can be created`() = endToEndTest { templateProperties ->
        highlightTest(
            baseAssetUrl = templateProperties.baseAssetUrl,
            highlightUrl = "/api/index/guilds",
            pageUrl = PAGE_URL,
            sectionId = "guilds",
        )
    }
}
