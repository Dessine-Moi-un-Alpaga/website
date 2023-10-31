package be.alpago.website.modules.gallery

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.FirestorePageCollection
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.image.FirestoreImageMetadataRepository
import be.alpago.website.modules.image.ImageMetadata
import com.google.cloud.firestore.Firestore
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

private const val DOCUMENT = "gallery"

fun photoGalleryModule() = module {
    single<CrudRepository<ImageMetadata>>(
        named(PhotoGalleryRepositories.images)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreImageMetadataRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                DOCUMENT to "images"
            )
        )
    }

    single<PhotoGalleryPageModelFactory> {
        val animalRepository by inject<CrudRepository<Animal>>(
            named(AnimalRepositories.animal)
        )
        val imageRepository by inject<CrudRepository<ImageMetadata>>(
            named(PhotoGalleryRepositories.images)
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