package be.alpago.website.interfaces.ktor

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing

internal fun Application.assets() {
    routing {
        staticResources("/assets", "static.assets")
    }
}
