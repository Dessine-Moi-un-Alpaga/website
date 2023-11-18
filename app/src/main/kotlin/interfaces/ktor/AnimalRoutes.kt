package be.alpago.website.interfaces.ktor

import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.domain.Animal
import be.alpago.website.interfaces.koin.ANIMAL_REPOSITORY
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.animalRoutes() {
    val properties by inject<TemplateProperties>()
    val animalRepository by inject<Repository<Animal>>(
        named(ANIMAL_REPOSITORY)
    )
    val query by inject<ShowAnimalPage>()

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
