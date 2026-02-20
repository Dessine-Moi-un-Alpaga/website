package be.alpago.website.adapters.persistence.firestore

import be.alpago.website.domain.Animal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val BANNER_DESCRIPTION = "bannerDescription"
private const val COLOR = "color"
private const val DAM_LINK = "damLink"
private const val DAM_NAME = "damName"
private const val DATE_OF_BIRTH = "dateOfBirth"
private const val ID = "id"
private const val NAME = "name"
private const val PAGE_DESCRIPTION = "pageDescription"
private const val PREFIX = "prefix"
private const val SEX = "sex"
private const val SIRE_LINK = "sireLink"
private const val SIRE_NAME = "sireName"
private const val SOLD = "sold"
private const val SUBTITLE = "subtitle"
private const val SUFFIX = "suffix"
private const val TEXT = "text"
private const val THUMBNAIL_DESCRIPTION = "thumbnailDescription"
private const val TYPE = "type"
private const val TITLE = "title"

/**
 * @suppress
 */
class FirestoreAnimalTransformer : FirestoreAggregateTransformer<Animal>() {

    override fun fromDomain(aggregateRoot: Animal) = mapOf(
        BANNER_DESCRIPTION to aggregateRoot.bannerDescription,
        COLOR to aggregateRoot.color.name,
        DAM_LINK to aggregateRoot.dam.link,
        DAM_NAME to aggregateRoot.dam.name,
        DATE_OF_BIRTH to aggregateRoot.dateOfBirth.format(DateTimeFormatter.ISO_LOCAL_DATE),
        ID to aggregateRoot.id,
        NAME to aggregateRoot.name,
        PAGE_DESCRIPTION to aggregateRoot.pageDescription,
        PREFIX to aggregateRoot.prefix,
        SEX to aggregateRoot.sex.name,
        SIRE_LINK to aggregateRoot.sire.link,
        SIRE_NAME to aggregateRoot.sire.name,
        SOLD to aggregateRoot.sold,
        SUBTITLE to aggregateRoot.subtitle,
        SUFFIX to aggregateRoot.suffix,
        TEXT to aggregateRoot.text,
        THUMBNAIL_DESCRIPTION to aggregateRoot.thumbnailDescription,
        TITLE to aggregateRoot.title,
        TYPE to aggregateRoot.type.name,
    )

    override fun toDomain(fields: Map<String, Any?>) = Animal(
        bannerDescription = fields[BANNER_DESCRIPTION] as String,
        color = enumValueOf(fields[COLOR] as String),
        dam = Animal.Reference(
            link = fields[DAM_LINK] as String?,
            name = fields[DAM_NAME] as String,
        ),
        dateOfBirth = LocalDate.parse(fields[DATE_OF_BIRTH] as String, DateTimeFormatter.ISO_LOCAL_DATE),
        id = fields[ID] as String,
        name = fields[NAME] as String,
        pageDescription = fields[PAGE_DESCRIPTION] as String,
        prefix = fields[PREFIX] as String,
        sex = enumValueOf(fields[SEX] as String),
        sire = Animal.Reference(
            link = fields[SIRE_LINK] as String?,
            name = fields[SIRE_NAME] as String,
        ),
        sold = fields[SOLD] as Boolean,
        subtitle = fields[SUBTITLE] as String,
        suffix = fields[SUFFIX] as String,
        text = fields[TEXT] as String,
        thumbnailDescription = fields[THUMBNAIL_DESCRIPTION] as String,
        title = fields[TITLE] as String,
        type = enumValueOf(fields[TYPE] as String),
    )
}
