package be.alpago.website.interfaces.ktor.routes

import io.ktor.server.application.Application

suspend fun Application.routes() {
    animalRoutes()
    emailRoute()
    factsheetRoutes()
    fiberAnalysisRoutes()
    indexRoutes()
    newsRoutes()
    photoGalleryRoutes()
}
