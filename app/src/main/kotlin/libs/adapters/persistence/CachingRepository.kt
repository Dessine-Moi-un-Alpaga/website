package be.alpago.website.libs.adapters.persistence

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.ports.persistence.Repository
import java.util.Collections.synchronizedMap

private data class FindByCacheKey(
    val argumentName: String,
    val argumentValue: Any?,
)

class CachingRepository<T : AggregateRoot>(private val delegate: Repository<T>) : Repository<T> {

    private val findAllCache: MutableMap<Nothing?, List<T>> = synchronizedMap(mutableMapOf())

    private val findByCache: MutableMap<FindByCacheKey, List<T>> = synchronizedMap(mutableMapOf())

    private val getCache: MutableMap<String, T> = synchronizedMap(mutableMapOf())

    override suspend fun create(aggregateRoot: T) {
        delegate.create(aggregateRoot)
        findAllCache.clear()
        findByCache.clear()
        getCache.remove(aggregateRoot.id)
    }

    override suspend fun delete(id: String) {
        delegate.delete(id)
        findAllCache.clear()
        findByCache.clear()
        getCache.remove(id)
    }

    override suspend fun deleteAll() {
        delegate.deleteAll()
        findAllCache.clear()
        findByCache.clear()
        getCache.clear()
    }

    override suspend fun findAll(): List<T> {
        var result = findAllCache[null]

        if (result == null) {
            result = delegate.findAll()
            findAllCache[null] = result
        }

        return result
    }

    override suspend fun findBy(field: String, value: String): List<T> {
        val key = FindByCacheKey(field, value)
        var result = findByCache[key]

        if (result == null) {
            result = delegate.findBy(field, value)
            findByCache[key] = result
        }

        return result
    }

    override suspend fun get(id: String): T {
        var result = getCache[id]

        if (result == null) {
            result = delegate.get(id)
            getCache[id] = result
        }

        return result
    }
}
