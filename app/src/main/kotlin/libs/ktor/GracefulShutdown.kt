package be.alpago.website.libs.ktor

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.engine.ApplicationEngine

private const val GRACE_PERIOD_MILLIS: Long = 500
private const val TIMEOUT_MILLIS: Long = 500

fun ApplicationEngine.registerShutdownHook() {
    Runtime.getRuntime().addShutdownHook(
        Thread {
            stop(GRACE_PERIOD_MILLIS, TIMEOUT_MILLIS)
        }
    )
}

fun Application.registerCloseable(closeable: AutoCloseable) {
    environment.monitor.subscribe(ApplicationStopped) {
        closeable.close()
    }
}
