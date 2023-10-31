package be.alpago.website

import be.alpago.website.libs.environment.environment
import be.alpago.website.libs.ktor.caching
import be.alpago.website.libs.ktor.registerShutdownHook
import be.alpago.website.libs.repository.firestore
import be.alpago.website.modules.animal.animals
import be.alpago.website.modules.assets.assets
import be.alpago.website.modules.auth.authentication
import be.alpago.website.modules.email.email
import be.alpago.website.modules.factsheet.factsheets
import be.alpago.website.modules.gallery.photoGallery
import be.alpago.website.modules.index.index
import be.alpago.website.modules.news.news
import be.alpago.website.modules.validation.validation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main() {
    with(
        embeddedServer(
            factory = CIO,
            port = 8080,
            module = Application::main
        )
    ) {
        registerShutdownHook()
        start(
            wait = true
        )
    }
}

fun Application.main() {
    environment()
    authentication()
    validation()
    install(ContentNegotiation) { json() }
    caching()
    assets()
    firestore()
    animals()
    email()
    index()
    factsheets()
    news()
    photoGallery()
}
