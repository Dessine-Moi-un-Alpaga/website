package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.domain.Animal
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

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
