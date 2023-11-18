package be.alpago.website.interfaces.ktor

import be.alpago.website.application.usecases.InvalidEmailException
import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.domain.Email
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.emailRoute() {
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

        val service by inject<SendEmail>()

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
