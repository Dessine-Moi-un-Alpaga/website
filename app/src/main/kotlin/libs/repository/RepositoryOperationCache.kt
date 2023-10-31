package be.alpago.website.libs.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RepositoryOperationCache<T, U> {

    private val cache = mutableMapOf<T, U>()

    private val mutex = Mutex()

    suspend fun evictAll() {
        mutex.withLock {
            cache.clear()
        }
    }

    suspend fun evict(key: T) {
        mutex.withLock {
            cache.remove(key)
        }
    }

    suspend fun get(key: T? = null) = mutex.withLock {
        cache[key]
    }

    suspend fun put(key: T, value: U) {
        mutex.withLock {
            cache[key] = value
        }
    }
}
