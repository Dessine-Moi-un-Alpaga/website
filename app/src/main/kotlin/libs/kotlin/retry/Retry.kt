package be.alpago.website.libs.kotlin.retry

import be.alpago.website.libs.kotlin.i18n.ordinal
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("be.alpago.website.libs.kotlin.retry")

suspend fun <T> retry(
    times: Int = 3,
    initialDelayMillis: Long = 500,
    exponentialBackoffFactor: Int = 2,
    block: suspend () -> T
): T {
    var currentAttempt = 1
    var delayMillis = initialDelayMillis

    while (true) {
        try {
            return block()
        } catch (e: Exception) {
            if (currentAttempt > times) {
                throw e
            }

            logger.warn("Waiting ${delayMillis}ms before retrying an operation that failed for the ${ordinal(currentAttempt)} time", e)
            delay(delayMillis)
            currentAttempt++
            delayMillis *= exponentialBackoffFactor
        }
    }
}
