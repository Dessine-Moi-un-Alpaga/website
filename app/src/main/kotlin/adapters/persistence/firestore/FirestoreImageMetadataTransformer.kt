package be.alpago.website.adapters.persistence.firestore

import be.alpago.website.domain.ImageMetadata

private const val ID = "id"
private const val DESCRIPTION = "description"
private const val height = "height"
private const val path = "path"
private const val thumbnailPath = "thumbnailPath"
private const val width = "width"

class FirestoreImageMetadataTransformer : FirestoreAggregateTransformer<ImageMetadata> {

    override fun fromDomain(aggregateRoot: ImageMetadata) = mapOf(
        ID to aggregateRoot.id,
        DESCRIPTION to aggregateRoot.description,
        height to aggregateRoot.height,
        path to aggregateRoot.path,
        thumbnailPath to aggregateRoot.thumbnailPath,
        width to aggregateRoot.width
    )

    override fun toDomain(fields: Map<String, Any?>) = ImageMetadata(
        id = fields[ID] as String,
        description = fields[DESCRIPTION] as String,
        height = fields[height] as Int,
        path = fields[path] as String,
        thumbnailPath = fields[thumbnailPath] as String,
        width = fields[width] as Int,
    )
}
