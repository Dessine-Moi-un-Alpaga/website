package be.alpago.website.interfaces.ktor

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse

fun Application.autoHeadResponses() {
    install(AutoHeadResponse)
}
