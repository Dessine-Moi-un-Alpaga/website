package be.alpago.website.interfaces.ktor.routes

import io.ktor.server.application.Application

fun Application.routes() {
    animals()
    email()
    factsheets()
    fiberAnalyses()
    index()
    news()
    photoGallery()
}
