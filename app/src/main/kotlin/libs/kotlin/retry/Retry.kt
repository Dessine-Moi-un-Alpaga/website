package be.alpago.website.libs.kotlin.retry

import kotlinx.coroutines.delay

/**
 * Generic retry functionality for a given block of code.
 */
suspend fun <T> retry(
    options: RetryOptions,
    block: suspend () -> T
): T {
    var currentAttempt = 1
    var delayMillis = options.initialDelayMillis

    while (true) {
        try {
            return block()
        } catch (e: Exception) {
            if (currentAttempt > options.times) {
                throw e
            }

            delay(delayMillis)
            currentAttempt++
            delayMillis *= options.exponentialBackoffFactor
        }
    }
}

data class RetryOptions(
    val times: Int = 3,
    val initialDelayMillis: Long = 500,
    val exponentialBackoffFactor: Int = 2,
)
