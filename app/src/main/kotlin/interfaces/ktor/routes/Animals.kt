package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.domain.Animal
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.persistence.Repository
import be.alpago.website.libs.ktor.routes.managementRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Registers the HTTP endpoints for managing [Animal]s and displaying an [Animal]'s page.
 *
 * The endpoints are the following:
 * * `DELETE /api/animals`
 * * `DELETE /api/animals/{id}`
 * * `GET /api/animals`
 * * `GET /api/animals/{id}`
 * * `PUT /api/animals`
 * * `GET /animals/{id}.html`
 */
fun Application.animalRoutes() {
    val properties: TemplateProperties by dependencies
    val animalRepository: Repository<Animal> by dependencies
    val query: ShowAnimalPage by dependencies

    routing {
        get("/animals/{id}.html") {
            val id = call.parameters["id"]

            if (id == null) {
                call.response.status(HttpStatusCode.BadRequest)
            } else {
                val pageModel = query.execute(id)
                val template = LayoutTemplate(properties, pageModel)
                call.respondHtmlTemplate(template) { }
            }
        }
    }

    managementRoutes("/api/animals", animalRepository)
}
