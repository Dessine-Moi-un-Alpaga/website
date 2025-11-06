package be.alpago.website.e2e

import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.libs.di.getEnvironmentVariable
import be.alpago.website.modules
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.basicAuth
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import java.time.LocalDate
import kotlin.random.Random

private const val USERNAME = "test"
private const val PASSWORD = "**SECRET**"
private const val CREDENTIALS = "$USERNAME:\$2y\$12\$MEq8DuMADQU85PFGw844zuAVbEXOtyC1oTorFISvrsPIoQ9Rn92qy"

private val FAKER_CONFIGURATION = fakerConfig {
    randomClassInstance {
        typeGenerator<LocalDate> {
            LocalDate.of(
                Random.nextInt(2016, 2025),
                Random.nextInt(1, 12),
                Random.nextInt(1, 28),
            )
        }
    }
}

val FAKER = Faker(FAKER_CONFIGURATION)

fun ApplicationTestBuilder.createJsonClient() = createClient {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.mockAuthentication() {
    dependencies.provide {
        AuthenticationProperties(credentials = CREDENTIALS)
    }
}

fun Application.mockFirestoreEnvironment() {
    dependencies.provide {
        FirestoreProperties(
            environmentName = FAKER.random.randomString(12),
            project = getEnvironmentVariable("DMUA_PROJECT"),
            url = getEnvironmentVariable("DMUA_FIRESTORE_URL"),
        )
    }
}

fun mockTemplates() = TemplateProperties(
    baseAssetUrl = getEnvironmentVariable("DMUA_BASE_ASSET_URL"),
    emailAddress = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
    includeTestIds = true
)

fun HttpRequestBuilder.authenticate() {
    basicAuth(USERNAME, PASSWORD)
}

fun endToEndTest(block: suspend ApplicationTestBuilder.(TemplateProperties) -> Unit) {
    val templateProperties = mockTemplates()

    testApplication {
        application {
            mockAuthentication()
            mockFirestoreEnvironment()
            dependencies.provide { templateProperties }
            modules()
        }

        block(templateProperties)
    }
}
