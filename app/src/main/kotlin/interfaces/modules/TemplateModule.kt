package be.alpago.website.interfaces.modules

import be.alpago.website.getEnvironmentVariable
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.register
import io.ktor.server.application.Application

fun Application.templateModule() {
    register {
        TemplateProperties(
            baseAssetUrl = getEnvironmentVariable("DMUA_BASE_ASSET_URL"),
            emailAddress = getEnvironmentVariable("DMUA_EMAIL_ADDRESS")
        )
    }
}
