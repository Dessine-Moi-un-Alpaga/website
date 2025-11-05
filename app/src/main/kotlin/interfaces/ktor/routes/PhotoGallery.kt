package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

suspend fun Application.photoGallery() {
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
