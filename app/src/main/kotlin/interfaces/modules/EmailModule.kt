package be.alpago.website.interfaces.modules

import be.alpago.website.adapters.sendgrid.SendGridEmailService
import be.alpago.website.adapters.sendgrid.SendGridProperties
import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.getEnvironmentVariable
import be.alpago.website.register
import io.ktor.server.application.Application

fun Application.emailModule() {
    register<SendEmail> {
        SendGridEmailService(
            SendGridProperties(
                address = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
                apiKey = getEnvironmentVariable("DMUA_SEND_GRID_API_KEY"),
            )
        )
    }
}
