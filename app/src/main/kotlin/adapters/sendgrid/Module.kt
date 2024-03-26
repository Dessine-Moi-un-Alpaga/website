package be.alpago.website.adapters.sendgrid

import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.libs.di.getEnvironmentVariable
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.di.register
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

fun sendGrid() {
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
