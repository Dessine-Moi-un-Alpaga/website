package com.dessinemoiunalpaga.website.interfaces.ktor.routes

import com.dessinemoiunalpaga.website.application.usecases.ShowAnimalPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.LayoutTemplate
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.ktor.routes.managementRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Registers the HTTP endpoints related to the [Animal] pages:
 *
 * - `GET /animals/{id}.html`: returns the [Animal] page itself
 * - [Management Routes][managementRoutes] for the factsheet page's article:
 *     - `DELETE /api/animals`
 *     - `DELETE /api/animals/{id}`
 *     - `GET /api/animals`
 *     - `GET /api/animals/{id}`
 *     - `PUT /api/animals`
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
