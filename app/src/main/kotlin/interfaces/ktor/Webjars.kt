package be.alpago.website.interfaces.ktor

import be.alpago.website.libs.ktor.plugins.webjars.Webjars
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.webjars() {
    install(Webjars)
}
