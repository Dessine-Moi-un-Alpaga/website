package com.dessinemoiunalpaga.website.adapters.persistence.firestore

import com.dessinemoiunalpaga.website.domain.ImageMetadata

private const val ID = "id"
private const val DESCRIPTION = "description"
private const val HEIGHT = "height"
private const val PATH = "path"
private const val THUMBNAIL_PATH = "thumbnailPath"
private const val WIDTH = "width"

internal class FirestoreImageMetadataTransformer : FirestoreAggregateTransformer<ImageMetadata>() {

    override fun fromDomain(aggregateRoot: ImageMetadata) = mapOf(
        ID to aggregateRoot.id,
        DESCRIPTION to aggregateRoot.description,
        HEIGHT to aggregateRoot.height,
        PATH to aggregateRoot.path,
        THUMBNAIL_PATH to aggregateRoot.thumbnailPath,
        WIDTH to aggregateRoot.width
    )

    override fun toDomain(fields: Map<String, Any?>) = ImageMetadata(
        id = fields[ID] as String,
        description = fields[DESCRIPTION] as String,
        height = fields[HEIGHT] as Int,
        path = fields[PATH] as String,
        thumbnailPath = fields[THUMBNAIL_PATH] as String,
        width = fields[WIDTH] as Int,
    )
}
