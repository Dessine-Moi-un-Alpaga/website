package be.alpago.website.libs.ktor

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.EmbeddedServer

private const val GRACE_PERIOD_MILLIS: Long = 500
private const val TIMEOUT_MILLIS: Long = 500

/**
 * Registers a shutdown hook that gracefully stops the server.
 */
fun <T : ApplicationEngine, U : ApplicationEngine.Configuration> EmbeddedServer<T, U>.registerShutdownHook() {
    Runtime.getRuntime().addShutdownHook(
        Thread {
            stop(GRACE_PERIOD_MILLIS, TIMEOUT_MILLIS)
        }
    )
}
