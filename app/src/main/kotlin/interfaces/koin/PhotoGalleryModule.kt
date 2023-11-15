package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreImageMetadataTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.PhotoGalleryPageModelFactory
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
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    CachingRepository(
                        FirestoreRepository(
                            client = client,
                            collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                            properties,
                            transformer = FirestoreImageMetadataTransformer(),
                        )
                    )
                }

                single<PhotoGalleryPageModelFactory> {
                    val animalRepository by inject<Repository<Animal>>(
                        named(ANIMAL_REPOSITORY)
                    )
                    val imageRepository by inject<Repository<ImageMetadata>>(
                        named(PHOTO_GALLERY_IMAGE_REPOSITORY)
                    )
                    PhotoGalleryPageModelFactory(animalRepository, imageRepository)
                }
            }
        )
    }
}
