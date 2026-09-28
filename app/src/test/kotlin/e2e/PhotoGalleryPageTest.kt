package com.dessinemoiunalpaga.website.e2e

import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.TEST_ATTRIBUTE
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

private const val PAGE_URL = "/photos.html"

class PhotoGalleryPageTest {

    @Test
    fun `the photo gallery page is initially empty`() = endToEndTest {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[$TEST_ATTRIBUTE=photos] [$TEST_ATTRIBUTE=photos-photo]").shouldBeEmpty()
    }

    @Test
    fun `photos can be created`() = endToEndTest { templateProperties ->
        photoGalleryTest(
            baseAssetUrl = templateProperties.baseAssetUrl,
            galleryUrl = "/api/gallery/photos",
            pageUrl = PAGE_URL,
            sectionId = "photos",
        )
    }
}
