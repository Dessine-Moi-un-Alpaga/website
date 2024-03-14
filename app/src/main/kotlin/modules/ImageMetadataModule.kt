package be.alpago.website.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreImageMetadataTransformer
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.register
import io.ktor.server.application.Application

const val IMAGE_METADATA_TRANSFORMER = "imageMetadata"

fun Application.imageMetadataModule() {
    register<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER) {
        FirestoreImageMetadataTransformer()
    }
}
