package be.alpago.website

import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.koin.animalModule
import be.alpago.website.interfaces.koin.articleModule
import be.alpago.website.interfaces.koin.authenticationModule
import be.alpago.website.interfaces.koin.emailModule
import be.alpago.website.interfaces.koin.environmentModule
import be.alpago.website.interfaces.koin.factsheetModule
import be.alpago.website.interfaces.koin.fiberAnalysisModule
import be.alpago.website.interfaces.koin.firestoreModule
import be.alpago.website.interfaces.koin.highlightModule
import be.alpago.website.interfaces.koin.imageMetadataModule
import be.alpago.website.interfaces.koin.indexModule
import be.alpago.website.interfaces.koin.newsModule
import be.alpago.website.interfaces.koin.photoGallery
import be.alpago.website.interfaces.koin.templateModule
import be.alpago.website.interfaces.ktor.animalRoutes
import be.alpago.website.interfaces.ktor.assets
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.emailRoute
import be.alpago.website.interfaces.ktor.factsheetRoutes
import be.alpago.website.interfaces.ktor.fiberAnalysisRoutes
import be.alpago.website.interfaces.ktor.httpCaching
import be.alpago.website.interfaces.ktor.indexRoutes
import be.alpago.website.interfaces.ktor.newsRoutes
import be.alpago.website.interfaces.ktor.photoGalleryRoutes
import be.alpago.website.interfaces.ktor.registerShutdownHook
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.validation
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
    environmentModule()

    assets()

    authenticationModule()
    authentication()

    httpCaching()

    i18n()

    serialization()

    validation()

    firestoreModule()
    templateModule()

    articleModule()
    highlightModule()
    imageMetadataModule()

    animalModule()
    animalRoutes()

    fiberAnalysisModule()
    fiberAnalysisRoutes()

    emailModule()
    emailRoute()

    factsheetModule()
    factsheetRoutes()

    indexModule()
    indexRoutes()

    newsModule()
    newsRoutes()

    photoGallery()
    photoGalleryRoutes()
}
