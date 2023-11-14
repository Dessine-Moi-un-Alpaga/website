package be.alpago.website.modules.firestore

import be.alpago.website.libs.firestore.createHttpClient
import be.alpago.website.libs.ktor.registerCloseable
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin

fun firestoreModule() = module {
    single<HttpClient> {
        createHttpClient()
    }
}

fun Application.firestore() {
    koin {
        modules(be.alpago.website.modules.firestore.firestoreModule())
    }

    val client by inject<HttpClient>()
    registerCloseable {
        client.close()
    }
}
