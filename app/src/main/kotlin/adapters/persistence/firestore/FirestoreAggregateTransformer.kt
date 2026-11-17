package com.dessinemoiunalpaga.website.adapters.persistence.firestore

import com.dessinemoiunalpaga.website.libs.domain.AggregateRoot

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

    private fun unwrap(fields: Map<String, Value>) = fields.mapValues  {
        val value = it.value

        when (value) {
            is BooleanValue -> value.booleanValue
            is IntValue     -> value.integerValue
            is NullValue    -> null
            is StringValue  -> value.stringValue
        }
    }

    private fun wrap(fields: Map<String, Any?>) = fields.mapValues {
        val value = it.value

        when (value) {
            is Boolean  -> BooleanValue(value)
            is Int      -> IntValue(value)
            is Nothing? -> NullValue(null)
            is String   -> StringValue(value)
            else        -> throw RuntimeException("Unknown value type: $value")
        }
    }
}
