package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowPhotoGalleryPageQuery
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val PHOTO_GALLERY_IMAGE_REPOSITORY = "pages/gallery/images"

private const val PHOTO_GALLERY_IMAGE_COLLECTION = "pages/gallery/images"

fun Application.photoGallery() {
    koin {
        modules(
            module {
                single<Repository<ImageMetadata>>(
                    named(PHOTO_GALLERY_IMAGE_REPOSITORY)
                ) {
                    CachingRepository(
                        FirestoreRepository(
                            client = get<HttpClient>(),
                            collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                            properties = get<FirestoreProperties>(),
                            transformer = get<FirestoreAggregateTransformer<ImageMetadata>>(
                                named(IMAGE_METADATA_TRANSFORMER)
                            )
                        )
                    )
                }

                single<ShowPhotoGalleryPage> {
                    ShowPhotoGalleryPageQuery(
                        animalRepository = get<Repository<Animal>>(
                            named(ANIMAL_REPOSITORY)
                        ),
                        imageRepository = CachingRepository(
                            FirestoreRepository(
                                client = get<HttpClient>(),
                                collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                                properties = get<FirestoreProperties>(),
                                transformer = get<FirestoreAggregateTransformer<ImageMetadata>>(
                                    named(IMAGE_METADATA_TRANSFORMER)
                                )
                            )
                        )
                    )
                }
            }
        )
    }
}
