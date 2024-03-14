package be.alpago.website.interfaces.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowPhotoGalleryPageQuery
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.inject
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.register
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

const val PHOTO_GALLERY_IMAGE_REPOSITORY = "pages/gallery/images"

private const val PHOTO_GALLERY_IMAGE_COLLECTION = "pages/gallery/images"

fun Application.photoGalleryModule() {
    register<Repository<ImageMetadata>>(PHOTO_GALLERY_IMAGE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER),
            )
        )
    }

    register<ShowPhotoGalleryPage> {
        ShowPhotoGalleryPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            imageRepository = CachingRepository(
                FirestoreRepository(
                    client = inject<HttpClient>(),
                    collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                    properties = inject<FirestoreProperties>(),
                    transformer = inject<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER),
                )
            )
        )
    }
}
