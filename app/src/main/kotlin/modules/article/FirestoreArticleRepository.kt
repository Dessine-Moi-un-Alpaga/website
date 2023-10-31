package be.alpago.website.modules.article

import be.alpago.website.libs.repository.AbstractFirestoreCrudRepository
import be.alpago.website.libs.repository.await
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore

private object Fields {
    const val id = "id"
    const val banner = "banner"
    const val bannerDescription = "bannerDescription"
    const val sectionTitle = "sectionTitle"
    const val subtitle = "subtitle"
    const val text = "text"
    const val title = "title"
}

class FirestoreArticleRepository(
    collection: String,
    db: Firestore,
    environment: String,
    vararg subCollections: Pair<String, String>,
) : AbstractFirestoreCrudRepository<Article>(
    collection,
    db,
    environment,
    *subCollections,
) {

    override fun DocumentSnapshot.toDomain() = Article(
        id = getString(Fields.id)!!,
        banner = getString(Fields.banner),
        bannerDescription = getString(Fields.bannerDescription),
        sectionTitle = getString(Fields.sectionTitle)!!,
        subtitle = getString(Fields.subtitle),
        text = getString(Fields.text)!!,
        title = getString(Fields.title)
    )

    override suspend fun DocumentReference.fromDomain(aggregateRoot: Article) {
        set(
            mapOf(
                Fields.id to aggregateRoot.id,
                Fields.banner to aggregateRoot.banner,
                Fields.bannerDescription to aggregateRoot.bannerDescription,
                Fields.sectionTitle to aggregateRoot.sectionTitle,
                Fields.subtitle to aggregateRoot.subtitle,
                Fields.text to aggregateRoot.text,
                Fields.title to aggregateRoot.title
            )
        ).await()
    }
}
