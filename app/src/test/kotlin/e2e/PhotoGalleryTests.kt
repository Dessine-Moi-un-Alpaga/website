package be.alpago.website.e2e

import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.style.Photoswipe
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.ApplicationTestBuilder
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

suspend fun ApplicationTestBuilder.photoGalleryTest(
    baseAssetUrl: String,
    galleryUrl: String,
    pageUrl: String,
    sectionId: String,
) {
    val imageMetadata = newRandomImageMetadata()
    createImageMetadata(galleryUrl, imageMetadata)

    val document = getPhotoGalleryPage(pageUrl)
    document.select("[data-test-id=$sectionId]") shouldHaveSize 1
    document.select("[data-test-id=$sectionId-photo]") shouldHaveSize 1
    document.select("[data-test-id=$sectionId-photo-image]").attr(Photoswipe.height) shouldBe "${imageMetadata.height}"
    document.select("[data-test-id=$sectionId-photo-image]").attr(Photoswipe.width) shouldBe "${imageMetadata.width}"
    document.select("[data-test-id=$sectionId-photo-image]").attr("href") shouldBe "${baseAssetUrl}/${imageMetadata.path}"
    document.select("[data-test-id=$sectionId-photo-thumbnail]").attr("alt") shouldBe imageMetadata.description
    document.select("[data-test-id=$sectionId-photo-thumbnail]").attr("src") shouldBe "${baseAssetUrl}/${imageMetadata.thumbnailPath}"
}

private suspend fun ApplicationTestBuilder.createImageMetadata(galleryUrl: String, imageMetadata: ImageMetadata) {
    val jsonClient = createJsonClient()
    val response = jsonClient.put(galleryUrl) {
        contentType(ContentType.Application.Json)
        authenticate()
        setBody(Json.encodeToString(imageMetadata))
    }
    response shouldHaveStatus HttpStatusCode.OK
}

private suspend fun ApplicationTestBuilder.getPhotoGalleryPage(pageUrl: String): Document {
    val response = client.get(pageUrl)
    response shouldHaveStatus HttpStatusCode.OK

    return Jsoup.parse(response.bodyAsText())
}

private fun newRandomImageMetadata() = FAKER.randomProvider.randomClassInstance<ImageMetadata>()
