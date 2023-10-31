package be.alpago.website.modules.email

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.emailRoute() {
    routing {
        emailValidation()

        val emailService by inject<EmailService>()

        post("/api/email") {
            val email = call.receive<Email>()
            emailService.send(email)
            call.response.status(HttpStatusCode.NoContent)
        }
    }
}
