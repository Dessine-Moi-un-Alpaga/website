package be.alpago.website

import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.ktor.routes.animalRoutes
import be.alpago.website.interfaces.ktor.assets
import be.alpago.website.interfaces.ktor.authentication
import be.alpago.website.interfaces.ktor.routes.emailRoute
import be.alpago.website.interfaces.ktor.routes.factsheetRoutes
import be.alpago.website.interfaces.ktor.routes.fiberAnalysisRoutes
import be.alpago.website.interfaces.ktor.httpCaching
import be.alpago.website.interfaces.ktor.routes.indexRoutes
import be.alpago.website.interfaces.ktor.routes.newsRoutes
import be.alpago.website.interfaces.ktor.routes.photoGalleryRoutes
import be.alpago.website.interfaces.ktor.registerShutdownHook
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.validation
import be.alpago.website.modules.animalModule
import be.alpago.website.modules.articleModule
import be.alpago.website.modules.authenticationModule
import be.alpago.website.modules.emailModule
import be.alpago.website.modules.factsheetModule
import be.alpago.website.modules.fiberAnalysisModule
import be.alpago.website.modules.firestoreModule
import be.alpago.website.modules.highlightModule
import be.alpago.website.modules.imageMetadataModule
import be.alpago.website.modules.indexModule
import be.alpago.website.modules.newsModule
import be.alpago.website.modules.photoGalleryModule
import be.alpago.website.modules.templateModule
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

    photoGalleryModule()
    photoGalleryRoutes()
}
