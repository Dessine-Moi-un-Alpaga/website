package be.alpago.website.e2e

import be.alpago.website.domain.Highlight
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
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

suspend fun ApplicationTestBuilder.highlightTest(
    baseAssetUrl: String,
    highlightUrl: String,
    pageUrl: String,
    sectionId: String,
) {
    val jsonClient = createJsonClient()
    val highlight = newRandomHighlight()

    jsonClient.createHighlight(highlightUrl, highlight)

    val page = getHighlightPage(pageUrl)

    page.highlights(sectionId) shouldHaveSize 1
    page.highlight(sectionId) shouldHaveSize 1
    page.thumbnailLink(sectionId) shouldBe highlight.link
    page.thumbnailDescription(sectionId) shouldBe highlight.thumbnailDescription
    page.thumbnailImage(sectionId) shouldBe "${baseAssetUrl}/${highlight.thumbnail}"
    page.thumbnailTitleLink(sectionId) shouldBe highlight.link
    page.thumbnailTitle(sectionId) shouldBe highlight.title
    page.highlightText(sectionId) shouldBe highlight.text
    page.highlightButtonLink(sectionId) shouldBe highlight.link
}

private fun newRandomHighlight() = FAKER.randomProvider.randomClassInstance<Highlight>()

private suspend fun HttpClient.createHighlight(url: String, highlight: Highlight) {
    val response = put(url) {
        contentType(ContentType.Application.Json)
        authenticate()
        setBody(Json.encodeToString(highlight))
    }
    response shouldHaveStatus HttpStatusCode.OK
}

private suspend fun ApplicationTestBuilder.getHighlightPage(pageUrl: String): Document {
    val response = client.get(pageUrl)
    response shouldHaveStatus HttpStatusCode.OK

    return Jsoup.parse(response.bodyAsText())
}

private fun Document.highlights(sectionId: String)  = select("[data-test-id=$sectionId]")
private fun Document.highlight(sectionId: String) = select("[data-test-id=$sectionId-highlight]")
private fun Document.thumbnailLink(sectionId: String) = select("[data-test-id=$sectionId-highlight-thumbnail]").attr("href")
private fun Document.thumbnailDescription(sectionId: String) = select("[data-test-id=$sectionId-highlight-thumbnail-image]").attr("alt")
private fun Document.thumbnailImage(sectionId: String) = select("[data-test-id=$sectionId-highlight-thumbnail-image]").attr("src")
private fun Document.thumbnailTitleLink(sectionId: String) = select("[data-test-id=$sectionId-highlight-title]").attr("href")
private fun Document.thumbnailTitle(sectionId: String) = select("[data-test-id=$sectionId-highlight-title]").text()
private fun Document.highlightText(sectionId: String) = select("[data-test-id=$sectionId-highlight-text]").text()
private fun Document.highlightButtonLink(sectionId: String) = select("[data-test-id=$sectionId-highlight-button]").attr("href")
