package be.alpago.website.e2e

import be.alpago.website.domain.Article
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

suspend fun ApplicationTestBuilder.articleTest(
    articleUrl: String,
    baseAssetUrl: String,
    pageUrl: String,
    sectionId: String,
) {
    val contentAttribute = "data-test-id=${sectionId}-contents"
    val content = FAKER.random.randomString(250)
    val article: Article = FAKER.randomProvider.randomClassInstance {
        namedParameterGenerator("text") {
            "<p $contentAttribute>$content</p>"
        }
    }

    createArticle(articleUrl, article)

    val document = getArticlePage(pageUrl)
    document.select("[data-test-id=${sectionId}]") shouldHaveSize 1
    document.select("[data-test-id=${sectionId}-section-title]").text() shouldBe article.sectionTitle
    document.select("[data-test-id=${sectionId}-title]").text() shouldBe article.title
    document.select("[data-test-id=${sectionId}-subtitle]").text() shouldBe article.subtitle
    document.select("[data-test-id=${sectionId}-banner]").attr("alt") shouldBe article.bannerDescription
    document.select("[data-test-id=${sectionId}-banner]").attr("src") shouldBe "${baseAssetUrl}/${article.banner}"
    document.select("[$contentAttribute]").text() shouldBe content
}

private suspend fun ApplicationTestBuilder.createArticle(articleUrl: String, article: Article) {
    val jsonClient = createJsonClient()
    val response = jsonClient.put(articleUrl) {
        contentType(ContentType.Application.Json)
        authenticate()
        setBody(Json.encodeToString(article))
    }

    response shouldHaveStatus HttpStatusCode.OK
}

private suspend fun ApplicationTestBuilder.getArticlePage(pageUrl: String): Document {
    val response = client.get(pageUrl)
    response shouldHaveStatus HttpStatusCode.OK

    return Jsoup.parse(response.bodyAsText())
}
