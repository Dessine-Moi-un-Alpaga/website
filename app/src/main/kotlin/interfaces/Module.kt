package be.alpago.website.interfaces

import be.alpago.website.interfaces.kotlinx.html.templates
import be.alpago.website.interfaces.ktor.ktor
import io.ktor.server.application.Application

suspend fun Application.interfaces() {
    templates()
    ktor()
}
