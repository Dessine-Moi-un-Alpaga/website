package be.alpago.website.adapters.persistence.firestore

import be.alpago.website.libs.domain.AggregateRoot

/**
 * Provides the base contract for transforming [Map]s returned by Firestore to [AggregateRoot]s and vice versa.
 */
abstract class FirestoreAggregateTransformer<T : AggregateRoot> {

    protected abstract fun fromDomain(aggregateRoot: T): Map<String, Any?>

    fun fromFirestore(firestoreFields: Map<String, Value>) = toDomain(
        unwrap(firestoreFields)
    )

    protected abstract fun toDomain(fields: Map<String, Any?>): T

    fun toFirestore(aggregateRoot: T) = wrap(
        fromDomain(aggregateRoot)
    )

    private fun unwrap(fields: Map<String, Value>) = fields.mapValues {
        when (it.value) {
            is BooleanValue -> (it.value as BooleanValue).booleanValue
            is IntValue     -> (it.value as IntValue).integerValue
            is NullValue    -> null
            is StringValue  -> (it.value as StringValue).stringValue
        }
    }

    private fun wrap(fields: Map<String, Any?>) = fields.mapValues {
        when (it.value) {
            is Boolean  -> BooleanValue(it.value as Boolean)
            is Int      -> IntValue(it.value as Int)
            is Nothing? -> NullValue(null)
            is String   -> StringValue(it.value as String)
            else        -> throw RuntimeException("Unknown value type: ${it.value}")
        }
    }
}
