package be.alpago.website.libs.repository

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.AggregateRootNotFound
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore

private const val ENVIRONMENT_COLLECTION = "environments"

abstract class AbstractFirestoreCrudRepository<T : AggregateRoot>(
    private val collection: String,
    private val db: Firestore,
    private val environment: String,
    private vararg val subCollections: Pair<String, String>,
) : CrudRepository<T> {

    private fun collection(): CollectionReference {
        var collection = db.collection(ENVIRONMENT_COLLECTION)
            .document(environment)
            .collection(collection)

        for (subCollection in subCollections) {
            collection = collection.document(subCollection.first)
                .collection(subCollection.second)
        }

        return collection
    }

    override suspend fun createOrUpdate(aggregateRoot: T) {
        collection()
            .document(aggregateRoot.id)
            .fromDomain(aggregateRoot)
    }

    override suspend fun delete(id: String) {
        val document = collection().document(id)

        if (document.get().await().exists()) {
            document.delete().await()
        } else {
            throw AggregateRootNotFound()
        }
    }

    override suspend fun deleteAll() {
        collection()
            .get()
            .await()
            .documents
            .forEach {
                it.reference.delete().await()
            }
    }

    override suspend fun findAll() = collection()
        .listDocuments()
        .map { it.toDomain() }

    override suspend fun get(id: String): T {
        val document = collection()
            .document(id)
            .get()
            .await()

        return if (document.exists()) {
            document.toDomain()
        } else {
            throw AggregateRootNotFound()
        }
    }

    override suspend fun findBy(field: String, value: Any): List<T> =
        collection()
            .whereEqualTo(field, value)
            .get()
            .await()
            .map { it.toDomain() }

    private suspend fun DocumentReference.toDomain() = get().await().toDomain()

    abstract fun DocumentSnapshot.toDomain(): T

    abstract suspend fun DocumentReference.fromDomain(aggregateRoot: T)
}
