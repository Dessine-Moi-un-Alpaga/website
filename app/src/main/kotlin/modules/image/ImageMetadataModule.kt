package be.alpago.website.modules.image

import be.alpago.website.domain.image.FirestoreImageMetadataTransformer
import be.alpago.website.domain.image.ImageMetadata
import be.alpago.website.libs.firestore.FirestoreAggregateTransformer
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val IMAGE_METADATA_TRANSFORMER = "imageMetadata"

private fun imageMetadataModule() = module {
    single<FirestoreAggregateTransformer<ImageMetadata>>(
        named(IMAGE_METADATA_TRANSFORMER)
    ) {
        FirestoreImageMetadataTransformer()
    }
}

fun Application.imageMetadata() {
    koin {
        modules(imageMetadataModule())
    }
}
