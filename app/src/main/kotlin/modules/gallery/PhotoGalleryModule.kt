package be.alpago.website.modules.gallery

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.image.FirestoreImageMetadataTransformer
import be.alpago.website.domain.image.ImageMetadata
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.firestore.FirestoreRepository
import be.alpago.website.modules.animal.ANIMAL_REPOSITORY
import be.alpago.website.pages.gallery.PhotoGalleryPageModelFactory
import be.alpago.website.pages.gallery.photoGalleryRoutes
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val PHOTO_GALLERY_IMAGE_REPOSITORY = "pages/gallery/images"

private const val PHOTO_GALLERY_IMAGE_COLLECTION = "pages/gallery/images"

fun photoGalleryModule() = module {
    single<Repository<ImageMetadata>>(
        named(PHOTO_GALLERY_IMAGE_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreImageMetadataTransformer(),
                url = environment.firestoreUrl,
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

fun Application.photoGallery() {
    koin {
        modules(
            photoGalleryModule()
        )
    }

    photoGalleryRoutes()
}
