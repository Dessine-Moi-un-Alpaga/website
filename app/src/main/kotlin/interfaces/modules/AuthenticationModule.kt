package be.alpago.website.interfaces.modules

import be.alpago.website.getEnvironmentVariable
import be.alpago.website.interfaces.ktor.AuthenticationProperties
import be.alpago.website.register
import io.ktor.server.application.Application

fun Application.authenticationModule() {
    register {
        AuthenticationProperties(
            credentials = getEnvironmentVariable("DMUA_CREDENTIALS"),
        )
    }
}
