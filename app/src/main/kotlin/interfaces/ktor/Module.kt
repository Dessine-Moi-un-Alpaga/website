package be.alpago.website.interfaces.ktor

import be.alpago.website.interfaces.ktor.routes.routes
import io.ktor.server.application.Application

internal suspend fun Application.ktor() {
    assets()
    authentication()
    autoHeadResponses()
    httpCaching()
    routes()
    serialization()
    validation()
    webjars()
}
