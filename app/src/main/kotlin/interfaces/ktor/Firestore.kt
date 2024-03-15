package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

private const val DEFAULT_ENVIRONMENT_NAME = "local"
private const val DEFAULT_FIRESTORE_URL = "https://firestore.googleapis.com"

fun Application.firestore() {
    register<HttpClient> {
        createHttpClient().also {
            registerCloseable(it)
        }
    }

    register<FirestoreProperties> {
        FirestoreProperties(
            environmentName = getEnvironmentVariable("DMUA_ENVIRONMENT", DEFAULT_ENVIRONMENT_NAME),
            project = getEnvironmentVariable("DMUA_PROJECT"),
            url = getEnvironmentVariable("DMUA_FIRESTORE_URL", DEFAULT_FIRESTORE_URL),
        )
    }
}
