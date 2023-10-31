package be.alpago.website.libs.repository

import be.alpago.website.libs.domain.AggregateRoot

interface CrudRepository<T : AggregateRoot> {

    suspend fun createOrUpdate(aggregateRoot: T)

    suspend fun delete(id: String)

    suspend fun deleteAll()

    suspend fun findAll(): List<T>

    suspend fun findBy(field: String, value: Any): List<T>

    suspend fun get(id: String): T
}
