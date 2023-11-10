package be.alpago.website.modules.animal

import be.alpago.website.libs.repository.FirestoreAggregateTransformer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val ANIMAL_COLLECTION = "animals"

private object AnimalFields {
    const val bannerDescription = "bannerDescription"
    const val color = "color"
    const val damLink = "damLink"
    const val damName = "damName"
    const val dateOfBirth = "dateOfBirth"
    const val id = "id"
    const val name = "name"
    const val pageDescription = "pageDescription"
    const val prefix = "prefix"
    const val sex = "sex"
    const val sireLink = "sireLink"
    const val sireName = "sireName"
    const val sold = "sold"
    const val subtitle = "subtitle"
    const val suffix = "suffix"
    const val text = "text"
    const val thumbnailDescription = "thumbnailDescription"
    const val type = "type"
    const val title = "title"
}

object FirestoreAnimalTransformer : FirestoreAggregateTransformer<Animal> {

    override fun fromDomain(aggregateRoot: Animal) = mapOf(
        AnimalFields.bannerDescription to aggregateRoot.bannerDescription,
        AnimalFields.color to aggregateRoot.color.name,
        AnimalFields.damLink to aggregateRoot.dam.link,
        AnimalFields.damName to aggregateRoot.dam.name,
        AnimalFields.dateOfBirth to aggregateRoot.dateOfBirth.format(DateTimeFormatter.ISO_LOCAL_DATE),
        AnimalFields.id to aggregateRoot.id,
        AnimalFields.name to aggregateRoot.name,
        AnimalFields.pageDescription to aggregateRoot.pageDescription,
        AnimalFields.prefix to aggregateRoot.prefix,
        AnimalFields.sex to aggregateRoot.sex.name,
        AnimalFields.sireLink to aggregateRoot.sire.link,
        AnimalFields.sireName to aggregateRoot.sire.name,
        AnimalFields.sold to aggregateRoot.sold,
        AnimalFields.subtitle to aggregateRoot.subtitle,
        AnimalFields.suffix to aggregateRoot.suffix,
        AnimalFields.text to aggregateRoot.text,
        AnimalFields.thumbnailDescription to aggregateRoot.thumbnailDescription,
        AnimalFields.title to aggregateRoot.title,
        AnimalFields.type to aggregateRoot.type.name,
    )

    override fun toDomain(fields: Map<String, Any?>) = Animal(
        bannerDescription = fields[AnimalFields.bannerDescription] as String,
        color = enumValueOf(fields[AnimalFields.color] as String),
        dam = Reference(
            link = fields[AnimalFields.damLink] as String?,
            name = fields[AnimalFields.damName] as String,
        ),
        dateOfBirth = LocalDate.parse(fields[AnimalFields.dateOfBirth] as String, DateTimeFormatter.ISO_LOCAL_DATE),
        id = fields[AnimalFields.id] as String,
        name = fields[AnimalFields.name] as String,
        pageDescription = fields[AnimalFields.pageDescription] as String,
        prefix = fields[AnimalFields.prefix] as String,
        sex = enumValueOf(fields[AnimalFields.sex] as String),
        sire = Reference(
            link = fields[AnimalFields.sireLink] as String?,
            name = fields[AnimalFields.sireName] as String,
        ),
        sold = fields[AnimalFields.sold] as Boolean,
        subtitle = fields[AnimalFields.subtitle] as String,
        suffix = fields[AnimalFields.suffix] as String,
        text = fields[AnimalFields.text] as String,
        thumbnailDescription = fields[AnimalFields.thumbnailDescription] as String,
        title = fields[AnimalFields.title] as String,
        type = enumValueOf(fields[AnimalFields.type] as String),
    )
}
