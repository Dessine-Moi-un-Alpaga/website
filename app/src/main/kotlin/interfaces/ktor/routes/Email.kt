package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.InvalidEmailException
import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.domain.Email
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

/**
 * Registers the HTTP endpoint for sending emails to the website administrator.
 *
 * The endpoint is : `POST /api/email`
 */
fun Application.emailRoute() {
    val service: SendEmail by dependencies

    routing {
        install(RequestValidation) {
            validate<Email> {
                if (it.isValid()) {
                    ValidationResult.Valid
                } else {
                    ValidationResult.Invalid("n/a")
                }
            }
        }

        post("/api/email") {
            val email = call.receive<Email>()

            try {
                service.send(email)
                call.response.status(HttpStatusCode.OK)
            } catch (e: InvalidEmailException) {
                call.response.status(HttpStatusCode.BadRequest)
            }
        }
    }
}
