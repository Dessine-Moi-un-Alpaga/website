package be.alpago.website

import be.alpago.website.modules.environment.environment
import be.alpago.website.libs.ktor.httpCaching
import be.alpago.website.libs.ktor.registerShutdownHook
import be.alpago.website.modules.firestore.firestore
import be.alpago.website.modules.animal.animals
import be.alpago.website.modules.article.articles
import be.alpago.website.modules.article.highlights
import be.alpago.website.modules.assets.assets
import be.alpago.website.modules.auth.authentication
import be.alpago.website.modules.email.email
import be.alpago.website.modules.fiber.fiberAnalyses
import be.alpago.website.modules.i18n.i18n
import be.alpago.website.modules.factsheet.factsheets
import be.alpago.website.modules.gallery.photoGallery
import be.alpago.website.modules.image.imageMetadata
import be.alpago.website.modules.serialization.serialization
import be.alpago.website.modules.index.index
import be.alpago.website.modules.news.news
import be.alpago.website.modules.validation.validation
import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

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

    assets()
    authentication()
    httpCaching()
    i18n()
    serialization()
    validation()

    firestore()

    animals()
    articles()
    fiberAnalyses()
    highlights()
    imageMetadata()

    email()
    factsheets()
    index()
    news()
    photoGallery()
}
