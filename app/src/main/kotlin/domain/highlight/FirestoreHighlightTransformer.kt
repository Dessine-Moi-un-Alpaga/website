package be.alpago.website.domain.highlight

import be.alpago.website.libs.firestore.FirestoreAggregateTransformer

class FirestoreHighlightTransformer : FirestoreAggregateTransformer<Highlight> {

    override fun fromDomain(aggregateRoot: Highlight) = mapOf(
        ID to aggregateRoot.id,
        LINK to aggregateRoot.link,
        TEXT to aggregateRoot.text,
        THUMBNAIL to aggregateRoot.thumbnail,
        THUMBNAIL_DESCRIPTION to aggregateRoot.thumbnailDescription,
        TITLE to aggregateRoot.title,
    )

    override fun toDomain(fields: Map<String, Any?>) = Highlight(
        id = fields[ID] as String,
        link = fields[LINK] as String,
        text = fields[TEXT] as String?,
        thumbnail = fields[THUMBNAIL] as String,
        thumbnailDescription = fields[THUMBNAIL_DESCRIPTION] as String,
        title = fields[TITLE] as String,
    )
    
    private companion object Fields {
        const val ID = "id"
        const val LINK = "link"
        const val TEXT = "text"
        const val THUMBNAIL = "thumbnail"
        const val THUMBNAIL_DESCRIPTION = "thumbnailDescription"
        const val TITLE = "title"
    }
}
