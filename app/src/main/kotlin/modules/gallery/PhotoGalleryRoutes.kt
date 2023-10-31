package be.alpago.website.modules.gallery

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.image.ImageMetadata
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.photoGalleryRoutes() {
    routing {
        val environment by inject<Environment>()
        val pageModelFactory by inject<PhotoGalleryPageModelFactory>()

        get("/photos.html") {
            val pageModel = pageModelFactory.create()
            val template = LayoutTemplate(environment, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val imageRepository by inject<CrudRepository<ImageMetadata>>(
        named(PhotoGalleryRepositories.images)
    )
    managementRoutes("/api/gallery/photos", imageRepository)
}
