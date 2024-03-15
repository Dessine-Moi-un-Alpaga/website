package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.sendgrid.SendGridEmailService
import be.alpago.website.adapters.sendgrid.SendGridProperties
import be.alpago.website.application.usecases.InvalidEmailException
import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.domain.Email
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.email() {
    register<HttpClientEngine> {
        CIO.create()
    }

    register<SendGridProperties> {
        SendGridProperties(
            address = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
            apiKey = getEnvironmentVariable("DMUA_SEND_GRID_API_KEY"),
        )
    }
    register<SendEmail> {
        SendGridEmailService(
            engine = inject<HttpClientEngine>(),
            properties = inject<SendGridProperties>(),
        )
    }

    val service by lazy { inject<SendEmail>() }

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
