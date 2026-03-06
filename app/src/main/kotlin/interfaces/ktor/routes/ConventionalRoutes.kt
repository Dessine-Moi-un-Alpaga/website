package be.alpago.website.interfaces.ktor.routes

import io.ktor.server.application.Application
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

internal const val APPLE_TOUCH_ICON = "apple-touch-icon.png"
internal const val FAVICON = "favicon.ico"
internal const val ROBOTS_TXT = "robots.txt"

/**
 * Registers the HTTP endpoints for some conventional website routes.
 *
 * The endpoints are the following:
 * * `GET /apple-touch-icon.png`
 * * `GET /favicon.ico`
 * * `GET /robots.txt`
 */
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

        get("/$ROBOTS_TXT") {
            call.respondRedirect(
                url = "/assets/$ROBOTS_TXT",
                permanent = true
            )
        }
    }
}
