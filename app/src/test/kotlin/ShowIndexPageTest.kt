package be.alpago.website

import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.Photoswipe
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.interfaces.ktor.animals
import be.alpago.website.interfaces.ktor.articles
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.clear
import be.alpago.website.interfaces.ktor.firestore
import be.alpago.website.interfaces.ktor.highlights
import be.alpago.website.interfaces.ktor.imageMetadata
import be.alpago.website.interfaces.ktor.index
import be.alpago.website.interfaces.ktor.inject
import be.alpago.website.interfaces.ktor.mock
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.templates
import be.alpago.website.interfaces.ktor.validation
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

private const val USERNAME = "test"
private const val PASSWORD = "**SECRET**"
private const val CREDENTIALS = "$USERNAME:\$2y\$12\$MEq8DuMADQU85PFGw844zuAVbEXOtyC1oTorFISvrsPIoQ9Rn92qy"

private const val ARTICLE_BANNER = "[data-test-id=article-banner]"
private const val ARTICLE_CONTENT_TEST_ATTRIBUTE = "data-test-id=article-contents"
private const val ARTICLE_CONTENT = "[$ARTICLE_CONTENT_TEST_ATTRIBUTE]"
private const val ARTICLE_SECTION = "[data-test-id=article]"
private const val ARTICLE_SECTION_TITLE = "[data-test-id=article-section-title]"
private const val ARTICLE_SUBTITLE = "[data-test-id=article-subtitle]"
private const val ARTICLE_TTTLE = "[data-test-id=article-title]"

private const val GUILD_HIGHLIGHTS_SECTION = "[data-test-id=guilds]"
private const val GUILD_HIGHLIGHT = "[data-test-id=guilds-highlight]"
private const val GUILD_HIGHLIGHT_BUTTON = "[data-test-id=guilds-highlight-button]"
private const val GUILD_HIGHLIGHT_TEXT = "[data-test-id=guilds-highlight-text]"
private const val GUILD_HIGHLIGHT_THUMBNAIL = "[data-test-id=guilds-highlight-thumbnail]"
private const val GUILD_HIGHLIGHT_THUMBNAIL_IMAGE = "[data-test-id=guilds-highlight-thumbnail-image]"
private const val GUILD_HIGHLIGHT_TITLE = "[data-test-id=guilds-highlight-title]"

private const val NEWS_HIGHLIGHTS_SECTION = "[data-test-id=news]"
private const val NEWS_HIGHLIGHT = "[data-test-id=news-highlight]"
private const val NEWS_HIGHLIGHT_BUTTON = "[data-test-id=news-highlight-button]"
private const val NEWS_HIGHLIGHT_TEXT = "[data-test-id=news-highlight-text]"
private const val NEWS_HIGHLIGHT_THUMBNAIL = "[data-test-id=news-highlight-thumbnail]"
private const val NEWS_HIGHLIGHT_THUMBNAIL_IMAGE = "[data-test-id=news-highlight-thumbnail-image]"
private const val NEWS_HIGHLIGHT_TITLE = "[data-test-id=news-highlight-title]"

private const val TRAININGS_SECTION = "[data-test-id=trainings]"
private const val TRAINING = "[data-test-id=trainings-photo]"
private const val TRAINING_IMAGE = "[data-test-id=trainings-photo-image]"
private const val TRAINING_THUMBNAIL = "[data-test-id=trainings-photo-thumbnail]"

class ShowIndexPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private fun indexPageTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
        testApplication {
            application {
                authentication()

                mock<AuthenticationProperties> {
                    AuthenticationProperties(credentials = CREDENTIALS)
                }

                validation()
                i18n()
                serialization()

                firestore()
                templates()

                articles()
                highlights()
                imageMetadata()

                animals()
                index()
            }

            deleteAll()

            block()
        }
    }

    private suspend fun ApplicationTestBuilder.deleteAll() {
        val jsonClient = createJsonClient()
        var response = jsonClient.delete("/api/index/article") {
            basicAuth(USERNAME, PASSWORD)
        }
        response shouldHaveStatus HttpStatusCode.OK

        response = jsonClient.delete("/api/index/news") {
            basicAuth(USERNAME, PASSWORD)
        }
        response shouldHaveStatus HttpStatusCode.OK

        response = jsonClient.delete("/api/index/guilds") {
            basicAuth(USERNAME, PASSWORD)
        }
        response shouldHaveStatus HttpStatusCode.OK

        response = jsonClient.delete("/api/index/trainings") {
            basicAuth(USERNAME, PASSWORD)
        }
        response shouldHaveStatus HttpStatusCode.OK
    }

    private suspend fun ApplicationTestBuilder.createJsonClient() = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    @Test
    fun `the index page is initially empty`() = indexPageTestApplication {
        val response = client.get("/index.html")
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select(ARTICLE_SECTION).shouldBeEmpty()
        document.select("$NEWS_HIGHLIGHTS_SECTION $NEWS_HIGHLIGHT").shouldBeEmpty()
        document.select("$TRAININGS_SECTION $TRAINING").shouldBeEmpty()
        document.select("$GUILD_HIGHLIGHTS_SECTION $GUILD_HIGHLIGHT").shouldBeEmpty()
    }

    @Test
    fun `the main article can be created`() = indexPageTestApplication {
        val jsonClient = createJsonClient()
        val id = "${UUID.randomUUID()}"
        val banner = "banner"
        val bannerDescription = "bannerDescription"
        val sectionTitle = "sectionTitle"
        val subtitle = "subtitle"
        val content = "text"
        val text = "<p $ARTICLE_CONTENT_TEST_ATTRIBUTE>$content</p>"
        val title = "title"

        var response = jsonClient.put("/api/index/article") {
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

        response = client.get("/index.html")
        response shouldHaveStatus HttpStatusCode.OK

        val templateProperties = inject<TemplateProperties>()
        val baseAssetUrl = templateProperties.baseAssetUrl

        val document = Jsoup.parse(response.bodyAsText())

        document.select(ARTICLE_SECTION) shouldHaveSize 1
        document.select(ARTICLE_SECTION_TITLE).text() shouldBe sectionTitle
        document.select(ARTICLE_TTTLE).text() shouldBe title
        document.select(ARTICLE_SUBTITLE).text() shouldBe subtitle
        document.select(ARTICLE_BANNER).attr("alt") shouldBe bannerDescription
        document.select(ARTICLE_BANNER).attr("src") shouldBe "$baseAssetUrl/$banner"
        document.select(ARTICLE_CONTENT).text() shouldBe content
    }

    @Test
    fun `news highlights can be created`() = indexPageTestApplication {
        val jsonClient = createJsonClient()
        val id = "${UUID.randomUUID()}"
        val link = "link"
        val text = "text"
        val thumbnail = "thumbnail"
        val thumbnailDescription = "thumbnailDescription"
        val title = "title"

        var response = jsonClient.put("/api/index/news") {
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

        response = client.get("/index.html")
        response shouldHaveStatus HttpStatusCode.OK

        val templateProperties = inject<TemplateProperties>()
        val baseAssetUrl = templateProperties.baseAssetUrl

        val document = Jsoup.parse(response.bodyAsText())
        document.select(NEWS_HIGHLIGHTS_SECTION) shouldHaveSize 1
        document.select(NEWS_HIGHLIGHT) shouldHaveSize 1
        document.select(NEWS_HIGHLIGHT_THUMBNAIL).attr("href") shouldBe link
        document.select(NEWS_HIGHLIGHT_THUMBNAIL_IMAGE).attr("alt") shouldBe thumbnailDescription
        document.select(NEWS_HIGHLIGHT_THUMBNAIL_IMAGE).attr("src") shouldBe "$baseAssetUrl/$thumbnail"
        document.select(NEWS_HIGHLIGHT_TITLE).attr("href") shouldBe link
        document.select(NEWS_HIGHLIGHT_TITLE).text() shouldBe title
        document.select(NEWS_HIGHLIGHT_TEXT).text() shouldBe text
        document.select(NEWS_HIGHLIGHT_BUTTON).attr("href") shouldBe link
    }

    @Test
    fun `training photos can be created`() = indexPageTestApplication {
        val jsonClient = createJsonClient()
        val id = "${UUID.randomUUID()}"
        val description = "description"
        val height = 1200
        val path = "path"
        val thumbnailPath = "thumbnailPath"
        val width = 1600
        var response = jsonClient.put("/api/index/trainings") {
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

        response = client.get("/index.html")
        response shouldHaveStatus HttpStatusCode.OK

        val templateProperties = inject<TemplateProperties>()
        val baseAssetUrl = templateProperties.baseAssetUrl

        val document = Jsoup.parse(response.bodyAsText())
        document.select(TRAINING) shouldHaveSize 1

        document.select(TRAINING_IMAGE).attr(Photoswipe.height) shouldBe "$height"
        document.select(TRAINING_IMAGE).attr(Photoswipe.width) shouldBe "$width"
        document.select(TRAINING_IMAGE).attr("href") shouldBe "$baseAssetUrl/$path"
        document.select(TRAINING_THUMBNAIL).attr("alt") shouldBe description
        document.select(TRAINING_THUMBNAIL).attr("src") shouldBe "$baseAssetUrl/$thumbnailPath"
    }

    @Test
    fun `guild highlights can be created`() = indexPageTestApplication {
        val jsonClient = createJsonClient()
        val id = "${UUID.randomUUID()}"
        val link = "link"
        val text = "text"
        val thumbnail = "thumbnail"
        val thumbnailDescription = "thumbnailDescription"
        val title = "title"

        var response = jsonClient.put("/api/index/guilds") {
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

        response = client.get("/index.html")
        response shouldHaveStatus HttpStatusCode.OK

        val templateProperties = inject<TemplateProperties>()
        val baseAssetUrl = templateProperties.baseAssetUrl

        val document = Jsoup.parse(response.bodyAsText())
        document.select(GUILD_HIGHLIGHTS_SECTION) shouldHaveSize 1
        document.select(GUILD_HIGHLIGHT) shouldHaveSize 1
        document.select(GUILD_HIGHLIGHT_THUMBNAIL).attr("href") shouldBe link
        document.select(GUILD_HIGHLIGHT_THUMBNAIL_IMAGE).attr("alt") shouldBe thumbnailDescription
        document.select(GUILD_HIGHLIGHT_THUMBNAIL_IMAGE).attr("src") shouldBe "$baseAssetUrl/$thumbnail"
        document.select(GUILD_HIGHLIGHT_TITLE).attr("href") shouldBe link
        document.select(GUILD_HIGHLIGHT_TITLE).text() shouldBe title
        document.select(GUILD_HIGHLIGHT_TEXT).text() shouldBe text
        document.select(GUILD_HIGHLIGHT_BUTTON).attr("href") shouldBe link
    }
}
