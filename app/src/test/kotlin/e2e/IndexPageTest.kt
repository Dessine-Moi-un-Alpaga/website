package com.dessinemoiunalpaga.website.e2e

import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.TEST_ATTRIBUTE
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
        document.select("[$TEST_ATTRIBUTE=article]").shouldBeEmpty()
        document.select("[$TEST_ATTRIBUTE=news] [$TEST_ATTRIBUTE=news-highlight]").shouldBeEmpty()
        document.select("[$TEST_ATTRIBUTE=trainings] [$TEST_ATTRIBUTE=trainings-photo]").shouldBeEmpty()
        document.select("[$TEST_ATTRIBUTE=guilds] [$TEST_ATTRIBUTE=guilds-highlight]").shouldBeEmpty()
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
