package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.createHttpClient
import be.alpago.website.interfaces.ktor.registerCloseable
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin

fun Application.firestoreModule() {
    koin {
        modules(
            module {
                single<HttpClient> {
                    createHttpClient()
                }

                single<FirestoreProperties> {
                    FirestoreProperties(
                        environmentName = getPropertyOrNull("DMUA_ENVIRONMENT"),
                        project = getProperty("DMUA_PROJECT"),
                        url = getPropertyOrNull("DMUA_FIRESTORE_URL"),
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
