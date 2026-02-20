package be.alpago.website.libs.domain.ports.persistence

import be.alpago.website.libs.domain.AggregateRoot

/**
 * Base contract for a standard [Domain-Driven Aggregate Repository](https://martinfowler.com/eaaCatalog/repository.html)
 */
interface Repository<T : AggregateRoot> {

    /**
     * Deletes an Aggregate base on its root id.
     *
     */
    suspend fun delete(id: String)

    /**
     * Deletes all the existing Aggregates.
     */
    suspend fun deleteAll()

    /**
     * Lists all Aggregates.
     */
    suspend fun findAll(): List<T>

    /**
     * Returns every existing Aggregate that matches the given constraint.
     *
     * @param field the document field used to filter Aggregates
     * @param value the field value used to filter Aggregates
     */
    suspend fun findBy(field: String, value: String): List<T>

    /**
     * Returns the Aggregate matching the given id.
     *
     * @throws AggregateRootNotFound if no Aggregate exists that matches the given id
     */
    suspend fun get(id: String): T

    /**
     * Creates or updates the given Aggregate.
     */
    suspend fun save(aggregateRoot: T)
}
