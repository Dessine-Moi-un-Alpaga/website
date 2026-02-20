package be.alpago.website.interfaces.ktor.routes

import io.ktor.server.application.Application

internal suspend fun Application.routes() {
    animalRoutes()
    conventionalRoutes()
    emailRoute()
    factsheetRoutes()
    fiberAnalysisRoutes()
    indexRoutes()
    legacyRoutes()
    newsRoutes()
    photoGalleryRoutes()
}
