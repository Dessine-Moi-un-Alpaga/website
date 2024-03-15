package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreImageMetadataTransformer
import be.alpago.website.domain.ImageMetadata
import io.ktor.server.application.Application

const val IMAGE_METADATA_TRANSFORMER = "imageMetadata"

fun Application.imageMetadata() {
    register<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER) {
        FirestoreImageMetadataTransformer()
    }
}
