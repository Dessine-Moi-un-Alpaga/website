package be.alpago.website.modules.animal

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.repository.CrudRepository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.animalRoutes() {
    val environment by inject<Environment>()
    val animalRepository by inject<CrudRepository<Animal>>(
        named(AnimalRepositories.animal)
    )
    val fiberAnalysisRepository by inject<CrudRepository<FiberAnalysis>>(
        named(AnimalRepositories.fiberAnalyses)
    )

    routing {
        get("/animals/{id}.html") {
            val animalPageModelFactory = AnimalPageModelFactory(
                animalRepository = animalRepository,
                fiberAnalysisRepository = fiberAnalysisRepository
            )
            val id = call.parameters["id"]!!
            val pageModel = animalPageModelFactory.create(id)
            val template = LayoutTemplate(environment, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    managementRoutes("/api/animals", animalRepository)
    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
