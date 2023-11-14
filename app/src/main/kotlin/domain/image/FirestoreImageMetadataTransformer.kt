package be.alpago.website.domain.image

import be.alpago.website.libs.firestore.FirestoreAggregateTransformer

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
    
    private companion object Fields {
        const val ID = "id"
        const val DESCRIPTION = "description"
        const val height = "height"
        const val path = "path"
        const val thumbnailPath = "thumbnailPath"
        const val width = "width"
    }
}
