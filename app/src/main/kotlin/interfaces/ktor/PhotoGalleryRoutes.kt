package be.alpago.website.interfaces.ktor

import be.alpago.website.application.PhotoGalleryPageModelFactory
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.koin.PHOTO_GALLERY_IMAGE_REPOSITORY
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.photoGalleryRoutes() {
    routing {
        val properties by inject<TemplateProperties>()
        val pageModelFactory by inject<PhotoGalleryPageModelFactory>()

        get("/photos.html") {
            val pageModel = pageModelFactory.create()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val imageRepository by inject<Repository<ImageMetadata>>(
        named(PHOTO_GALLERY_IMAGE_REPOSITORY)
    )
    managementRoutes("/api/gallery/photos", imageRepository)
}
