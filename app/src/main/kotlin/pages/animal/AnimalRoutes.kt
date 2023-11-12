package be.alpago.website.pages.animal

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.fiber.FiberAnalysis
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.repository.Repository
import be.alpago.website.modules.animal.ANIMAL_REPOSITORY
import be.alpago.website.modules.fiber.FIBER_ANALYSIS_REPOSITORY
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.animalRoutes() {
    val environment by inject<Environment>()
    val animalRepository by inject<Repository<Animal>>(
        named(ANIMAL_REPOSITORY)
    )
    val fiberAnalysisRepository by inject<Repository<FiberAnalysis>>(
        named(FIBER_ANALYSIS_REPOSITORY)
    )

    routing {
        get("/animals/{id}.html") {
            val animalPageModelFactory = AnimalPageModelFactory(
                animalRepository = animalRepository,
                fiberAnalysisRepository = fiberAnalysisRepository
            )
            val id = call.parameters["id"]

            if (id == null) {
                call.response.status(HttpStatusCode.BadRequest)
            } else {
                val pageModel = animalPageModelFactory.create(id)
                val template = LayoutTemplate(environment, pageModel)
                call.respondHtmlTemplate(template) { }
            }
        }
    }

    managementRoutes("/api/animals", animalRepository)
}
