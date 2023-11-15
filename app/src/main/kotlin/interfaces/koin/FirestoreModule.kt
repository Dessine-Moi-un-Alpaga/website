package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.createHttpClient
import be.alpago.website.interfaces.ktor.registerCloseable
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin

private const val DEFAULT_ENVIRONMENT_NAME = "local"
private const val DEFAULT_FIRESTORE_URL = "https://firestore.googleapis.com"

fun Application.firestoreModule() {
    koin {
        modules(
            module {
                single<HttpClient> {
                    createHttpClient()
                }

                single<FirestoreProperties> {
                    FirestoreProperties(
                        environmentName = getProperty("DMUA_ENVIRONMENT", DEFAULT_ENVIRONMENT_NAME),
                        project = getProperty("DMUA_PROJECT"),
                        url = getProperty("DMUA_FIRESTORE_URL", DEFAULT_FIRESTORE_URL),
                    )
                }
            }
        )
    }

    val client by inject<HttpClient>()
    registerCloseable {
        client.close()
    }
}
