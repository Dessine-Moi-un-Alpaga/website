package be.alpago.website.interfaces.ktor

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import io.ktor.server.application.Application

fun Application.templates() {
    register {
        TemplateProperties(
            baseAssetUrl = getEnvironmentVariable("DMUA_BASE_ASSET_URL"),
            emailAddress = getEnvironmentVariable("DMUA_EMAIL_ADDRESS")
        )
    }
}
