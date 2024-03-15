package be.alpago.website

import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.ktor.animals
import be.alpago.website.interfaces.ktor.articles
import be.alpago.website.interfaces.ktor.assets
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.email
import be.alpago.website.interfaces.ktor.factsheets
import be.alpago.website.interfaces.ktor.fiberAnalyses
import be.alpago.website.interfaces.ktor.firestore
import be.alpago.website.interfaces.ktor.highlights
import be.alpago.website.interfaces.ktor.httpCaching
import be.alpago.website.interfaces.ktor.imageMetadata
import be.alpago.website.interfaces.ktor.index
import be.alpago.website.interfaces.ktor.newsModule
import be.alpago.website.interfaces.ktor.photoGallery
import be.alpago.website.interfaces.ktor.registerShutdownHook
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.templates
import be.alpago.website.interfaces.ktor.validation
import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun main() {
    with(
        embeddedServer(
            factory = CIO,
            port = 8080,
            module = Application::modules
        )
    ) {
        registerShutdownHook()
        start(
            wait = true
        )
    }
}

fun Application.modules() {
    assets()
    authentication()
    httpCaching()
    i18n()
    serialization()
    validation()

    firestore()
    templates()

    articles()
    highlights()
    imageMetadata()

    animals()
    fiberAnalyses()
    factsheets()
    index()
    newsModule()
    photoGallery()
    email()
}
