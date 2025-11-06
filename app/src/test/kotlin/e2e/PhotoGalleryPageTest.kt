package be.alpago.website.e2e

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
        document.select("[data-test-id=photos] [data-test-id=photos-photo]").shouldBeEmpty()
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
