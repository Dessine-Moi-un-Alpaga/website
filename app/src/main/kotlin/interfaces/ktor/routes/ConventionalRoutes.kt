package be.alpago.website.interfaces.ktor.routes

import io.ktor.server.application.Application
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private const val APPLE_TOUCH_ICON = "apple-touch-icon.png"
private const val FAVICON = "favicon.ico"

fun Application.conventionalRoutes() {
    routing {
        get("/$APPLE_TOUCH_ICON") {
            call.respondRedirect(
                url = "/assets/icons/$FAVICON",
                permanent = true,
            )
        }

        get("/$FAVICON") {
            call.respondRedirect(
                url = "/assets/icons/$APPLE_TOUCH_ICON",
                permanent = true,
            )
        }
    }
}
