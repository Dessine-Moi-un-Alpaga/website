package be.alpago.website.modules.image

import be.alpago.website.libs.repository.FirestoreAggregateTransformer

private object Fields {
    const val id = "id"
    const val description = "description"
    const val height = "height"
    const val path = "path"
    const val thumbnailPath = "thumbnailPath"
    const val width = "width"
}

object FirestoreImageMetadataTransformer : FirestoreAggregateTransformer<ImageMetadata> {

    override fun fromDomain(aggregateRoot: ImageMetadata) = mapOf(
        Fields.id to aggregateRoot.id,
        Fields.description to aggregateRoot.description,
        Fields.height to aggregateRoot.height,
        Fields.path to aggregateRoot.path,
        Fields.thumbnailPath to aggregateRoot.thumbnailPath,
        Fields.width to aggregateRoot.width
    )

    override fun toDomain(fields: Map<String, Any?>) = ImageMetadata(
        id = fields[Fields.id] as String,
        description = fields[Fields.description] as String,
        height = fields[Fields.height] as Int,
        path = fields[Fields.path] as String,
        thumbnailPath = fields[Fields.thumbnailPath] as String,
        width = fields[Fields.width] as Int,
    )
}
