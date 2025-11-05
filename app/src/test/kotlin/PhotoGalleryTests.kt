package be.alpago.website

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.Photoswipe
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.ApplicationTestBuilder
import org.jsoup.Jsoup
import java.util.UUID

suspend fun ApplicationTestBuilder.photoGalleryTest(
    galleryUrl: String,
    pageUrl: String,
    sectionId: String,
    templateProperties: TemplateProperties,
) {
    val gallery = "[data-test-id=$sectionId]"
    val photo = "[data-test-id=$sectionId-photo]"
    val image = "[data-test-id=$sectionId-photo-image]"
    val thumbnail = "[data-test-id=$sectionId-photo-thumbnail]"

    val jsonClient = createJsonClient()
    val id = "${UUID.randomUUID()}"
    val description = "description"
    val height = 1200
    val path = "path"
    val thumbnailPath = "thumbnailPath"
    val width = 1600
    var response = jsonClient.put(galleryUrl) {
        contentType(ContentType.Application.Json)
        basicAuth(USERNAME, PASSWORD)
        setBody("""
                {
                  "id": "$id",
                  "description": "$description",
                  "height": $height,
                  "path": "$path",
                  "thumbnailPath": "$thumbnailPath",
                  "width": $width
                }
            """.trimIndent())
    }
    response shouldHaveStatus HttpStatusCode.OK

    response = client.get(pageUrl)
    response shouldHaveStatus HttpStatusCode.OK

    val baseAssetUrl = templateProperties.baseAssetUrl

    val document = Jsoup.parse(response.bodyAsText())
    document.select(gallery) shouldHaveSize 1
    document.select(photo) shouldHaveSize 1
    document.select(image).attr(Photoswipe.height) shouldBe "$height"
    document.select(image).attr(Photoswipe.width) shouldBe "$width"
    document.select(image).attr("href") shouldBe "$baseAssetUrl/$path"
    document.select(thumbnail).attr("alt") shouldBe description
    document.select(thumbnail).attr("src") shouldBe "$baseAssetUrl/$thumbnailPath"
}
