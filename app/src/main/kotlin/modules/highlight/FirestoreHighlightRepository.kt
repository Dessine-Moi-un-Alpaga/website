package be.alpago.website.modules.highlight

import be.alpago.website.libs.repository.FirestoreAggregateTransformer

private object Fields {
    const val id = "id"
    const val link = "link"
    const val text = "text"
    const val thumbnail = "thumbnail"
    const val thumbnailDescription = "thumbnailDescription"
    const val title = "title"
}

object FirestoreHighlightTransformer : FirestoreAggregateTransformer<Highlight> {

    override fun fromDomain(aggregateRoot: Highlight) = mapOf(
        Fields.id to aggregateRoot.id,
        Fields.link to aggregateRoot.link,
        Fields.text to aggregateRoot.text,
        Fields.thumbnail to aggregateRoot.thumbnail,
        Fields.thumbnailDescription to aggregateRoot.thumbnailDescription,
        Fields.title to aggregateRoot.title,
    )

    override fun toDomain(fields: Map<String, Any?>) = Highlight(
        id = fields[Fields.id] as String,
        link = fields[Fields.link] as String,
        text = fields[Fields.text] as String?,
        thumbnail = fields[Fields.thumbnail] as String,
        thumbnailDescription = fields[Fields.thumbnailDescription] as String,
        title = fields[Fields.title] as String,
    )
}
