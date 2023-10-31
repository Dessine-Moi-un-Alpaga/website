package be.alpago.website.libs.repository

import com.google.api.core.ApiFuture
import com.google.api.core.ApiFutureCallback
import com.google.api.core.ApiFutures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> ApiFuture<T>.await(): T = suspendCancellableCoroutine { continuation ->
    ApiFutures.addCallback(this@await, object : ApiFutureCallback<T> {
        override fun onSuccess(result: T) = continuation.resume(result)
        override fun onFailure(throwable: Throwable) = continuation.resumeWithException(throwable)
    }, Dispatchers.IO.asExecutor())

    continuation.invokeOnCancellation {
        this@await.cancel(true)
    }
}
