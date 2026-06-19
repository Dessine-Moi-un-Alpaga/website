package be.alpago.website.adapters.persistence.firestore

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

/**
 * @suppress
 */
@Serializable(with = FirestoreValueSerializer::class)
sealed interface Value

/**
 * @suppress
 */
@Serializable
data class BooleanValue(
    val booleanValue: Boolean
) : Value

/**
 * @suppress
 */
@Serializable
data class StringValue(
    val stringValue: String
) : Value

/**
 * @suppress
 */
@Serializable
data class IntValue(
    val integerValue: Int
) : Value

/**
 * @suppress
 */
@Serializable
data class NullValue(
    val nullValue: Nothing?
) : Value

/**
 * @suppress
 */
object FirestoreValueSerializer : JsonContentPolymorphicSerializer<Value>(Value::class) {

    override fun selectDeserializer(element: JsonElement) = when {
        "booleanValue" in element.jsonObject -> BooleanValue.serializer()
        "integerValue" in element.jsonObject -> IntValue.serializer()
        "nullValue" in element.jsonObject    -> NullValue.serializer()
        "stringValue" in element.jsonObject  -> StringValue.serializer()
        else                                 -> throw RuntimeException("Unsupported value: ${element.jsonObject}")
    }
}
