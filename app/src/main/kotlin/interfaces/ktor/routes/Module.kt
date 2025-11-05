package be.alpago.website.interfaces.ktor.routes

import io.ktor.server.application.Application

suspend fun Application.routes() {
    animals()
    email()
    factsheets()
    fiberAnalyses()
    index()
    news()
    photoGallery()
}
