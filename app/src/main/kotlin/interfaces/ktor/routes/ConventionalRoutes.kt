package be.alpago.website.interfaces.ktor.routes

import io.ktor.server.application.Application
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private const val FAVICON = "favicon.ico"

fun Application.conventionalRoutes() {
    routing {
        get("/$FAVICON") {
            call.respondRedirect(
                url = "/assets/icons/$FAVICON",
                permanent = true,
            )
        }
    }
}
