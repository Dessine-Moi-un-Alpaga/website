package be.alpago.website.interfaces.ktor

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.EmbeddedServer

private const val GRACE_PERIOD_MILLIS: Long = 500
private const val TIMEOUT_MILLIS: Long = 500

fun <T : ApplicationEngine, U : ApplicationEngine.Configuration> EmbeddedServer<T, U>.registerShutdownHook() {
    Runtime.getRuntime().addShutdownHook(
        Thread {
            stop(GRACE_PERIOD_MILLIS, TIMEOUT_MILLIS)
        }
    )
}
