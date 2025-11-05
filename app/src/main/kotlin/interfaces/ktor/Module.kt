package be.alpago.website.interfaces.ktor

import be.alpago.website.interfaces.ktor.routes.routes
import be.alpago.website.libs.di.getEnvironmentVariable
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

suspend fun Application.ktor() {
    assets()
    authenticationProperties()
    authentication()
    httpCaching()
    routes()
    serialization()
    validation()
}

private fun Application.authenticationProperties() {
    dependencies {
        provide {
            AuthenticationProperties(
                credentials = getEnvironmentVariable("DMUA_CREDENTIALS"),
            )
        }
    }
}
