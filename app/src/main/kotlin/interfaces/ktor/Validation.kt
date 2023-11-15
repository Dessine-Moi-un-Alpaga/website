package be.alpago.website.interfaces.ktor

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.validation() {
    install(StatusPages) {
        exception<RequestValidationException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
