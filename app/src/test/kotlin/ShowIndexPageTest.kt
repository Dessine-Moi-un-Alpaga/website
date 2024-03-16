package be.alpago.website

import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

private const val USERNAME = "test"
private const val PASSWORD = "**SECRET**"
private const val CREDENTIALS = "$USERNAME:\$2y\$12\$MEq8DuMADQU85PFGw844zuAVbEXOtyC1oTorFISvrsPIoQ9Rn92qy"

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
        val response = jsonClient.delete("/api/index/article") {
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
        document.select("html > body > div section#article").isEmpty() shouldBe true
        document.select("html > body > div section#highlights:eq(0) > div.container > div > *").isEmpty() shouldBe true
        document.select("html > body > div section#photos > div.container > div > *").isEmpty() shouldBe true
        document.select("html > body > div section#highlights:eq(1) > div.container > div > *").isEmpty() shouldBe true
    }

    @Test
    fun `the main article can be created`() = indexPageTestApplication {
        val jsonClient = createJsonClient()
        val id = "${UUID.randomUUID()}"
        val banner = "banner"
        val bannerDescription = "bannerDescription"
        val sectionTitle = "sectionTitle"
        val subtitle = "subtitle"
        val contents = "text"
        val text = "<p>$contents</p>"
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
        document.select("html > body > div section#article > div.title").text() shouldBe sectionTitle
        document.select("html > body > div section#article > div.container > article > header > h2").text() shouldBe title
        document.select("html > body > div section#article > div.container > article > header > p").text() shouldBe subtitle
        document.select("html > body > div section#article > div.container > article > a > img").attr("alt") shouldBe bannerDescription
        document.select("html > body > div section#article > div.container > article > a > img").attr("src") shouldBe "$baseAssetUrl/$banner"
        document.select("html > body > div section#article > div.container > article > p").text() shouldBe contents
    }
}
