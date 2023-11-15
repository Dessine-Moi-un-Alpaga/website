package be.alpago.website.adapters.firestore

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable(with = FirestoreValueSerializer::class)
sealed interface Value

@Serializable
data class BooleanValue(
    val booleanValue: Boolean
) : Value

@Serializable
data class StringValue(
    val stringValue: String
) : Value

@Serializable
data class IntValue(
    val integerValue: Int
) : Value

@Serializable
data class NullValue(
    val nullValue: Nothing?
) : Value

object FirestoreValueSerializer : JsonContentPolymorphicSerializer<Value>(Value::class) {

    override fun selectDeserializer(element: JsonElement) = when {
        "booleanValue" in element.jsonObject -> BooleanValue.serializer()
        "integerValue" in element.jsonObject -> IntValue.serializer()
        "nullValue" in element.jsonObject    -> NullValue.serializer()
        "stringValue" in element.jsonObject  -> StringValue.serializer()
        else                                 -> throw RuntimeException("Unsupported value: ${element.jsonObject}")
    }
}
