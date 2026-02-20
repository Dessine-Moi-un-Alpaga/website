package be.alpago.website.libs.adapters.persistence

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.ports.persistence.Repository
import be.alpago.website.libs.kotlin.retry.retry

class RetryingRepository<T : AggregateRoot>(private val delegate: Repository<T>): Repository<T> {

    override suspend fun create(aggregateRoot: T) = retry { delegate.create(aggregateRoot) }

    override suspend fun delete(id: String) = retry { delegate.delete(id) }

    override suspend fun deleteAll() = retry { delegate.deleteAll() }

    override suspend fun findAll() = retry { delegate.findAll() }

    override suspend fun findBy(field: String, value: String) = retry { delegate.findBy(field, value) }

    override suspend fun get(id: String) = retry { delegate.get(id) }
}
