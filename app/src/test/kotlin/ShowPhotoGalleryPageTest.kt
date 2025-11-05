package be.alpago.website

import be.alpago.website.adapters.adapters
import be.alpago.website.application.queries.queries
import be.alpago.website.interfaces.interfaces
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

private const val PAGE_URL = "/photos.html"

class ShowPhotoGalleryPageTest {

    private fun photoGalleryPageTestApplication(block: suspend ApplicationTestBuilder.(TemplateProperties) -> Unit) {
        var templateProperties: TemplateProperties? = null

        testApplication {
            application {
                dependencies.provide {
                    AuthenticationProperties(credentials = CREDENTIALS)
                }

                adapters()
                queries()
                interfaces()

                templateProperties = dependencies.resolve<TemplateProperties>()
            }

            deleteAll()

            block(templateProperties!!)
        }
    }

    private suspend fun ApplicationTestBuilder.deleteAll() {
        delete("/api/gallery/photos")
    }

    @Test
    fun `the photo gallery page is initially empty`() = photoGalleryPageTestApplication {
        val response = client.get(PAGE_URL)
        response shouldHaveStatus HttpStatusCode.OK

        val document = Jsoup.parse(response.bodyAsText())
        document.select("[data-test-id=photos] [data-test-id=photos-photo]").shouldBeEmpty()
    }

    @Test
    fun `photos can be created`() = photoGalleryPageTestApplication { templateProperties ->
        photoGalleryTest(
            galleryUrl = "/api/gallery/photos",
            pageUrl = PAGE_URL,
            sectionId = "photos",
            templateProperties,
        )
    }
}
