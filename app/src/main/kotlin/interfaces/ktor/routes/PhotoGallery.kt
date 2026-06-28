package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.persistence.Repository
import be.alpago.website.libs.ktor.routes.managementRoutes
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Registers the HTTP endpoints related to the photo gallery page:
 *
 * - `GET /photos.html`: returns the photo gallery page itself
 * - [Management Routes][managementRoutes] for the photo gallery page's [ImageMetadata]:
 *     - `DELETE /api/gallery/photos`
 *     - `DELETE /api/gallery/photos/{id}`
 *     - `GET /api/gallery/photos`
 *     - `GET /api/gallery/photos/{id}`
 *     - `PUT /api/gallery/photos`
 */
suspend fun Application.photoGalleryRoutes() {
    val properties: TemplateProperties by dependencies
    val query: ShowPhotoGalleryPage by dependencies

    routing {
        get("/photos.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val imageRepository = dependencies.resolve<Repository<ImageMetadata>>(ShowPhotoGalleryPage::class.simpleName)
    managementRoutes("/api/gallery/photos", imageRepository)
}
