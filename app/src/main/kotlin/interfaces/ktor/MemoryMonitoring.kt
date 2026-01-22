package be.alpago.website.interfaces.ktor

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.EmbeddedServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("be.alpago.website.interfaces.ktor.MemoryMonitoring")

fun <T : ApplicationEngine, U : ApplicationEngine.Configuration> EmbeddedServer<T, U>.monitorMemoryUsage() {
    CoroutineScope(application.coroutineContext).launch {
        while (isActive) {
            logger.info(
                "[Memory] Max: {} - Total: {} - Free: {}",
                Runtime.getRuntime().maxMemory(),
                Runtime.getRuntime().totalMemory(),
                Runtime.getRuntime().freeMemory(),
            )
            delay(60000)
        }
    }
}
