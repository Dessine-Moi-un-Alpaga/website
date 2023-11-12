package be.alpago.website.libs.repository

import be.alpago.website.libs.domain.AggregateRoot

interface Repository<T : AggregateRoot> {

    suspend fun create(aggregateRoot: T)

    suspend fun delete(id: String)

    suspend fun deleteAll()

    suspend fun findAll(): List<T>

    suspend fun findBy(field: String, value: String): List<T>

    suspend fun get(id: String): T
}
