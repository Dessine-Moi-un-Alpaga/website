package be.alpago.website.interfaces.ktor

import be.alpago.website.interfaces.ktor.routes.routes
import be.alpago.website.libs.getEnvironmentVariable
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

suspend fun Application.ktor() {
    assets()
    authenticationProperties()
    authentication()
    autoHeadResponses()
    httpCaching()
    routes()
    serialization()
    validation()
    webjars()
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
