package be.alpago.website

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.ktor.inject
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

suspend fun ApplicationTestBuilder.highlightTest(
    highlightUrl: String,
    pageUrl: String,
    sectionId: String,
) {
    val section = "[data-test-id=$sectionId]"
    val highlight = "[data-test-id=$sectionId-highlight]"
    val highlightButton = "[data-test-id=$sectionId-highlight-button]"
    val highlightText = "[data-test-id=$sectionId-highlight-text]"
    val highlightThumbnail = "[data-test-id=$sectionId-highlight-thumbnail]"
    val highlightThumbnailImage = "[data-test-id=$sectionId-highlight-thumbnail-image]"
    val highlightThumbnailTitle = "[data-test-id=$sectionId-highlight-title]"

    val jsonClient = createJsonClient()
    val id = "${UUID.randomUUID()}"
    val link = "link"
    val text = "text"
    val thumbnail = "thumbnail"
    val thumbnailDescription = "thumbnailDescription"
    val title = "title"

    var response = jsonClient.put(highlightUrl) {
        contentType(ContentType.Application.Json)
        basicAuth(USERNAME, PASSWORD)
        setBody("""
                {
                  "id": "$id",
                  "link": "$link",
                  "text": "$text",
                  "thumbnail": "$thumbnail",
                  "thumbnailDescription": "$thumbnailDescription",
                  "title": "$title"
                }
            """.trimIndent())
    }

    response shouldHaveStatus HttpStatusCode.OK

    response = client.get(pageUrl)
    response shouldHaveStatus HttpStatusCode.OK

    val templateProperties = inject<TemplateProperties>()
    val baseAssetUrl = templateProperties.baseAssetUrl

    val document = Jsoup.parse(response.bodyAsText())
    document.select(section) shouldHaveSize 1
    document.select(highlight) shouldHaveSize 1
    document.select(highlightThumbnail).attr("href") shouldBe link
    document.select(highlightThumbnailImage).attr("alt") shouldBe thumbnailDescription
    document.select(highlightThumbnailImage).attr("src") shouldBe "$baseAssetUrl/$thumbnail"
    document.select(highlightThumbnailTitle).attr("href") shouldBe link
    document.select(highlightThumbnailTitle).text() shouldBe title
    document.select(highlightText).text() shouldBe text
    document.select(highlightButton).attr("href") shouldBe link
}
