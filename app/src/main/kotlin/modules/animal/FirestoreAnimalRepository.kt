package be.alpago.website.modules.animal

import be.alpago.website.libs.repository.AbstractFirestoreCrudRepository
import be.alpago.website.libs.repository.await
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
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

class FirestoreAnimalRepository(
    db: Firestore,
    environment: String
) : AbstractFirestoreCrudRepository<Animal>(
    ANIMAL_COLLECTION,
    db,
    environment
) {

    override fun DocumentSnapshot.toDomain() = Animal(
        bannerDescription = getString(AnimalFields.bannerDescription)!!,
        color = enumValueOf(getString(AnimalFields.color)!!),
        dam = Reference(
            name = getString(AnimalFields.damName)!!,
            link = getString(AnimalFields.damLink),
        ),
        dateOfBirth  = LocalDate.parse(getString(AnimalFields.dateOfBirth)!!, DateTimeFormatter.ISO_LOCAL_DATE),
        id = getString(AnimalFields.id)!!,
        name = getString(AnimalFields.name)!!,
        pageDescription = getString(AnimalFields.pageDescription)!!,
        prefix = getString(AnimalFields.prefix)!!,
        sex = enumValueOf(getString(AnimalFields.sex)!!),
        sire = Reference(
            name = getString(AnimalFields.sireName)!!,
            link = getString(AnimalFields.sireLink),
        ),
        sold = getBoolean(AnimalFields.sold)!!,
        subtitle = getString(AnimalFields.subtitle)!!,
        suffix = getString(AnimalFields.suffix)!!,
        text = getString(AnimalFields.text)!!,
        thumbnailDescription = getString(AnimalFields.thumbnailDescription)!!,
        title = getString(AnimalFields.title)!!,
        type = enumValueOf(getString(AnimalFields.type)!!),
    )

    override suspend fun DocumentReference.fromDomain(aggregateRoot: Animal) {
        set(
            mapOf(
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
                AnimalFields.type to aggregateRoot.type.name
            )
        ).await()
    }
}
