package be.alpago.website.libs.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE

object LocalDateSerializer : KSerializer<LocalDate> {

    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate = LocalDate.parse(
        decoder.decodeString(),
        FORMATTER
    )

    override fun serialize(encoder: Encoder, value: LocalDate) = encoder.encodeString(
        value.format(FORMATTER)
    )
}

typealias SerializableLocalDate = @Serializable(LocalDateSerializer::class) LocalDate
