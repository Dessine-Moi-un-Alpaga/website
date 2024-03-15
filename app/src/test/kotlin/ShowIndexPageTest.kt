package be.alpago.website

import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.interfaces.ktor.animals
import be.alpago.website.interfaces.ktor.articles
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.clear
import be.alpago.website.interfaces.ktor.firestore
import be.alpago.website.interfaces.ktor.highlights
import be.alpago.website.interfaces.ktor.imageMetadata
import be.alpago.website.interfaces.ktor.index
import be.alpago.website.interfaces.ktor.mock
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.templates
import be.alpago.website.interfaces.ktor.validation
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShowIndexPageTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    @Test
    fun `the index page is initially empty`() = testApplication {
        application {
            authentication()

            mock<AuthenticationProperties> {
                AuthenticationProperties(credentials = "**SECRET**")
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

        val response = client.get("/index.html")
        response shouldHaveStatus HttpStatusCode.OK
    }
}
