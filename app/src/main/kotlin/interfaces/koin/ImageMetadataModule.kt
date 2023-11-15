package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreImageMetadataTransformer
import be.alpago.website.domain.ImageMetadata
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val IMAGE_METADATA_TRANSFORMER = "imageMetadata"

fun Application.imageMetadataModule() {
    koin {
        modules(
            module {
                single<FirestoreAggregateTransformer<ImageMetadata>>(
                    named(IMAGE_METADATA_TRANSFORMER)
                ) {
                    FirestoreImageMetadataTransformer()
                }
            }
        )
    }
}
