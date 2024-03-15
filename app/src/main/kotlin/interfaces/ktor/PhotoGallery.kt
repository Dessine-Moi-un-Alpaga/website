package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowPhotoGalleryPageQuery
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

const val PHOTO_GALLERY_IMAGE_REPOSITORY = "pages/gallery/images"

private const val PHOTO_GALLERY_IMAGE_COLLECTION = "pages/gallery/images"

fun Application.photoGallery() {
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

    val properties by lazy { inject<TemplateProperties>() }
    val query by lazy { inject<ShowPhotoGalleryPage>() }

    routing {
        get("/photos.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val imageRepository by lazy { inject<Repository<ImageMetadata>>(PHOTO_GALLERY_IMAGE_REPOSITORY) }
    managementRoutes("/api/gallery/photos", imageRepository)
}
