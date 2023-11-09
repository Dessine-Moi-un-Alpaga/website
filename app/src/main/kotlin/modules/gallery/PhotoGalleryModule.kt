package be.alpago.website.modules.gallery

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.RestFirestoreCrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.image.FirestoreImageMetadataTransformer
import be.alpago.website.modules.image.ImageMetadata
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun photoGalleryModule() = module {
    single<CrudRepository<ImageMetadata>>(
        named(PhotoGalleryRepositories.images)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = PhotoGalleryRepositories.images,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreImageMetadataTransformer,
                url = environment.firestoreUrl,
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
