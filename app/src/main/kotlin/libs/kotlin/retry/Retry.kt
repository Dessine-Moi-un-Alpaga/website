package be.alpago.website.libs.kotlin.retry

import kotlinx.coroutines.delay

/**
 * Generic retry functionality for a given block of code, with an exponential backoff delay.
 */
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

            delay(delayMillis)
            currentAttempt++
            delayMillis *= exponentialBackoffFactor
        }
    }
}
