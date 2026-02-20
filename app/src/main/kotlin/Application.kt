package be.alpago.website

import be.alpago.website.adapters.adapters
import be.alpago.website.application.queries.queries
import be.alpago.website.interfaces.interfaces
import be.alpago.website.libs.ktor.registerShutdownHook
import be.alpago.website.libs.i18n4k.setLocale
import be.alpago.website.libs.slf4j.bridgeJavaUtilLoggingToSlf4j
import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun main() {
    bridgeJavaUtilLoggingToSlf4j()
    setLocale()

    with(
        embeddedServer(
            factory = CIO,
            port = 8080,
            module = Application::modules
        )
    ) {
        registerShutdownHook()
        start(
            wait = true
        )
    }
}

/**
 * @suppress
 */
suspend fun Application.modules() {
    adapters()
    queries()
    interfaces()
}
