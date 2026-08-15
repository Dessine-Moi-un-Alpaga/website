package com.dessinemoiunalpaga.website

import com.dessinemoiunalpaga.website.adapters.adapters
import com.dessinemoiunalpaga.website.application.queries.queries
import com.dessinemoiunalpaga.website.interfaces.interfaces
import com.dessinemoiunalpaga.website.libs.ktor.registerShutdownHook
import com.dessinemoiunalpaga.website.libs.slf4j.bridgeJavaUtilLoggingToSlf4j
import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun main() {
    bridgeJavaUtilLoggingToSlf4j()

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

internal suspend fun Application.modules() {
    adapters()
    queries()
    interfaces()
}
