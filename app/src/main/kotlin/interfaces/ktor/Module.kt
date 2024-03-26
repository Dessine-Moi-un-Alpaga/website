package be.alpago.website.interfaces.ktor

import be.alpago.website.interfaces.ktor.routes.routes
import be.alpago.website.libs.di.getEnvironmentVariable
import be.alpago.website.libs.di.register
import io.ktor.server.application.Application

fun Application.ktor() {
    assets()
    authentication()
    authenticationProperties()
    httpCaching()
    routes()
    serialization()
    validation()
}

private fun authenticationProperties() {
    register {
        AuthenticationProperties(
            credentials = getEnvironmentVariable("DMUA_CREDENTIALS"),
        )
    }
}
