package be.alpago.website.interfaces.koin

import io.ktor.server.application.Application
import org.koin.environmentProperties
import org.koin.ktor.plugin.koin

fun Application.environmentModule() {
    koin {
        environmentProperties()
    }
}
