package be.alpago.website.libs.environment

import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.environmentProperties
import org.koin.ktor.plugin.koin

private const val BASE_ASSET_URL = "DMUA_BASE_ASSET_URL"
private const val CREDENTIALS = "DMUA_CREDENTIALS"
private const val EMAIL_ADDRESS = "DMUA_EMAIL_ADDRESS"
private const val ENVIRONMENT = "DMUA_ENVIRONMENT"
private const val FIRESTORE_AUTHENTICATION_ENABLED = "DMUA_FIRESTORE_AUTHENTICATION_ENABLED"
private const val FIRESTORE_URL = "DMUA_FIRESTORE_URL"
private const val PROJECT = "DMUA_PROJECT"
private const val SEND_GRID_API_KEY = "DMUA_SEND_GRID_API_KEY"

private const val DEFAULT_EMAIL_ADDRESS = "contact@dessinemoiunalpaga.com"
private const val DEFAULT_FIRESTORE_URL = "https://firestore.googleapis.com"

fun environmentModule() = module {
    single<Environment> {
        Environment(
            baseAssetUrl = getProperty(BASE_ASSET_URL),
            credentials = getProperty(CREDENTIALS),
            emailAddress = getProperty(EMAIL_ADDRESS, DEFAULT_EMAIL_ADDRESS),
            firestoreAuthenticationEnabled = getProperty(FIRESTORE_AUTHENTICATION_ENABLED, "true").toBoolean(),
            firestoreUrl = getProperty(FIRESTORE_URL, DEFAULT_FIRESTORE_URL),
            name = getProperty(ENVIRONMENT),
            project = getProperty(PROJECT),
            sendGridApiKey = getProperty(SEND_GRID_API_KEY),
        )
    }
}

fun Application.environment() {
    koin {
        environmentProperties()
        modules(environmentModule())
    }
}
