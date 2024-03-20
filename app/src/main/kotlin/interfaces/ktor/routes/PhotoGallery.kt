package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.queries.PHOTO_GALLERY_IMAGE_REPOSITORY
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.photoGallery() {
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
