package be.alpago.website.adapters.persistence.firestore

import be.alpago.website.domain.Highlight

private const val ID = "id"
private const val LINK = "link"
private const val TEXT = "text"
private const val THUMBNAIL = "thumbnail"
private const val THUMBNAIL_DESCRIPTION = "thumbnailDescription"
private const val TITLE = "title"

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
}
