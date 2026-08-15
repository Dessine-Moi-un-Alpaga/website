package com.dessinemoiunalpaga.website.libs.adapters.persistence

import com.dessinemoiunalpaga.website.libs.domain.AggregateRoot
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.kotlin.retry.RetryOptions
import com.dessinemoiunalpaga.website.libs.kotlin.retry.retry

/**
 * A Repository decorator implementation that retries operations in case of failure.
 *
 * @see retry
 */
class RetryingRepository<T : AggregateRoot>(
    private val delegate: Repository<T>,
    private val options: RetryOptions = RetryOptions(),
): Repository<T> {

    override suspend fun delete(id: String) = retry(options) { delegate.delete(id) }

    override suspend fun deleteAll() = retry(options) { delegate.deleteAll() }

    override suspend fun findAll() = retry(options) { delegate.findAll() }

    override suspend fun findBy(field: String, value: String) = retry(options) { delegate.findBy(field, value) }

    override suspend fun get(id: String) = retry(options) { delegate.get(id) }

    override suspend fun save(aggregateRoot: T) = retry(options) { delegate.save(aggregateRoot) }
}
