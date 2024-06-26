package be.alpago.website.domain

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.kotlin.serialization.SerializableLocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Animal(
    val bannerDescription: String,
    val color: Color,
    val dam: Reference,
    val dateOfBirth: SerializableLocalDate,
    override val id: String,
    val name: String,
    val pageDescription: String,
    val prefix: String,
    val sex: Sex,
    val sire: Reference,
    val sold: Boolean,
    val subtitle: String,
    val suffix: String,
    val text: String,
    val thumbnailDescription: String,
    val title: String,
    val type: Type,
) : AggregateRoot {

    val fullName = "$prefix $name $suffix".trim()

    enum class Color {
        BAY_BLACK,
        DARK_FAWN,
        MEDIUM_FAWN,
        WHITE,
    }

    @Serializable
    data class Reference(val name: String, val link: String? = null)

    enum class Sex {
        FEMALE,
        MALE,
    }

    enum class Type {
        DOG,
        GELDING,
        MARE,
        STUD,
    }
}
