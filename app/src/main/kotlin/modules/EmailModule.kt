package be.alpago.website.modules

import be.alpago.website.adapters.sendgrid.SendGridEmailService
import be.alpago.website.adapters.sendgrid.SendGridProperties
import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.getEnvironmentVariable
import be.alpago.website.inject
import be.alpago.website.register
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.server.application.Application

fun Application.emailModule() {
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
}
