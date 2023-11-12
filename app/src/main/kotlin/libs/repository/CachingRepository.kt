package be.alpago.website.libs.repository

import be.alpago.website.libs.domain.AggregateRoot

private data class FindByCacheKey(
    val argumentName: String,
    val argumentValue: Any?,
)

class CachingRepository<T : AggregateRoot>(private val delegate: Repository<T>) : Repository<T> {

    private val findAllCache = RepositoryOperationCache<String?, List<T>>()

    private val findByCache = RepositoryOperationCache<FindByCacheKey, List<T>>()

    private val getCache = RepositoryOperationCache<String, T>()

    override suspend fun create(aggregateRoot: T) {
        delegate.create(aggregateRoot)
        findAllCache.evictAll()
        findByCache.evictAll()
        getCache.evict(aggregateRoot.id)
    }

    override suspend fun delete(id: String) {
        delegate.delete(id)
        findAllCache.evictAll()
        findByCache.evictAll()
        getCache.evict(id)
    }

    override suspend fun deleteAll() {
        delegate.deleteAll()
        findAllCache.evictAll()
        findByCache.evictAll()
        getCache.evictAll()
    }

    override suspend fun findAll(): List<T> {
        var result = findAllCache.get()

        if (result == null) {
            result = delegate.findAll()
            findAllCache.put(null, result)
        }

        return result
    }

    override suspend fun findBy(field: String, value: String): List<T> {
        val key = FindByCacheKey(field, value)
        var result = findByCache.get(key)

        if (result == null) {
            result = delegate.findBy(field, value)
            findByCache.put(key, result)
        }

        return result
    }

    override suspend fun get(id: String): T {
        var result = getCache.get(id)

        if (result == null) {
            result = delegate.get(id)
            getCache.put(id, result)
        }

        return result
    }
}
