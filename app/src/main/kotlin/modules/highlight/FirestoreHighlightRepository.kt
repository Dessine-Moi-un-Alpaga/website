package be.alpago.website.modules.highlight

import be.alpago.website.libs.repository.AbstractFirestoreCrudRepository
import be.alpago.website.libs.repository.await
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore

private object Fields {
    const val id = "id"
    const val link = "link"
    const val text = "text"
    const val thumbnail = "thumbnail"
    const val thumbnailDescription = "thumbnailDescription"
    const val title = "title"
}

class FirestoreHighlightRepository(
    collection: String,
    db: Firestore,
    environment: String,
    vararg subCollections: Pair<String, String>,
) : AbstractFirestoreCrudRepository<Highlight>(
    collection,
    db,
    environment,
    *subCollections,
) {

    override fun DocumentSnapshot.toDomain() = Highlight(
        id = getString(Fields.id)!!,
        link = getString(Fields.link)!!,
        text = getString(Fields.text),
        thumbnail = getString(Fields.thumbnail)!!,
        thumbnailDescription = getString(Fields.thumbnailDescription)!!,
        title = getString(Fields.title)!!,

    )

    override suspend fun DocumentReference.fromDomain(aggregateRoot: Highlight) {
        set(
            mapOf(
                Fields.id to aggregateRoot.id,
                Fields.link to aggregateRoot.link,
                Fields.text to aggregateRoot.text,
                Fields.thumbnail to aggregateRoot.thumbnail,
                Fields.thumbnailDescription to aggregateRoot.thumbnailDescription,
                Fields.title to aggregateRoot.title,
            )
        ).await()
    }
}
