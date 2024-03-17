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

suspend fun ApplicationTestBuilder.articleTest(
    articleUrl: String,
    pageUrl: String,
    sectionId: String,
) {
    val articleBanner = "[data-test-id=${sectionId}-banner]"
    val articleContentTestAttribute = "data-test-id=${sectionId}-contents"
    val articleContent = "[$articleContentTestAttribute]"
    val articleSection = "[data-test-id=${sectionId}]"
    val articleSectionTitle = "[data-test-id=${sectionId}-section-title]"
    val articleSubtitle = "[data-test-id=${sectionId}-subtitle]"
    val articleTitle = "[data-test-id=${sectionId}-title]"

    val jsonClient = createJsonClient()
    val id = "${UUID.randomUUID()}"
    val banner = "banner"
    val bannerDescription = "bannerDescription"
    val sectionTitle = "sectionTitle"
    val subtitle = "subtitle"
    val content = "text"
    val text = "<p $articleContentTestAttribute>$content</p>"
    val title = "title"

    var response = jsonClient.put(articleUrl) {
        contentType(ContentType.Application.Json)
        basicAuth(USERNAME, PASSWORD)
        setBody("""
                {
                  "id": "$id",
                  "banner": "$banner",
                  "bannerDescription": "$bannerDescription",
                  "sectionTitle": "$sectionTitle",
                  "subtitle": "$subtitle",
                  "text": "$text",
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
    document.select(articleSection) shouldHaveSize 1
    document.select(articleSectionTitle).text() shouldBe sectionTitle
    document.select(articleTitle).text() shouldBe title
    document.select(articleSubtitle).text() shouldBe subtitle
    document.select(articleBanner).attr("alt") shouldBe bannerDescription
    document.select(articleBanner).attr("src") shouldBe "$baseAssetUrl/$banner"
    document.select(articleContent).text() shouldBe content
}
