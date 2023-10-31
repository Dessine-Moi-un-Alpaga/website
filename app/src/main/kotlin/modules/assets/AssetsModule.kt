package be.alpago.website.modules.assets

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing

fun Application.assets() {
    routing {
        staticResources("/assets", "static.assets")
        staticResources("/webjars", "static.webjars")
    }
}
