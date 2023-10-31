package be.alpago.website.modules.image

import be.alpago.website.libs.repository.AbstractFirestoreCrudRepository
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore

private object Fields {
    const val id = "id"
    const val description = "description"
    const val height = "height"
    const val path = "path"
    const val thumbnailPath = "thumbnailPath"
    const val width = "width"
}

class FirestoreImageMetadataRepository(
    collection: String,
    db: Firestore,
    environment: String,
    vararg subCollections: Pair<String, String>,
) : AbstractFirestoreCrudRepository<ImageMetadata>(
    collection,
    db,
    environment,
    *subCollections,
) {

    override fun DocumentSnapshot.toDomain() = ImageMetadata(
        id = getString(Fields.id)!!,
        description = getString(Fields.description)!!,
        height = getString(Fields.height)!!.toInt(),
        path = getString(Fields.path)!!,
        thumbnailPath = getString(Fields.thumbnailPath)!!,
        width = getString(Fields.width)!!.toInt(),
    )

    override suspend fun DocumentReference.fromDomain(aggregateRoot: ImageMetadata) {
        set(
            mapOf(
                Fields.id to aggregateRoot.id,
                Fields.description to aggregateRoot.description,
                Fields.height to "${aggregateRoot.height}",
                Fields.path to aggregateRoot.path,
                Fields.thumbnailPath to aggregateRoot.thumbnailPath,
                Fields.width to "${aggregateRoot.width}"
            )
        )
    }
}
