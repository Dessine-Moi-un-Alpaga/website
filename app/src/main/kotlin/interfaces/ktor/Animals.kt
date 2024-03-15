package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAnimalTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowAnimalPageQuery
import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

const val ANIMAL_REPOSITORY = "animals"

private const val ANIMAL_COLLECTION = "animals"

fun Application.animals() {
    register<Repository<Animal>>(ANIMAL_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = ANIMAL_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = FirestoreAnimalTransformer(),
            )
        )
    }

    register<ShowAnimalPage> {
        ShowAnimalPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            fiberAnalysisRepository = inject<Repository<FiberAnalysis>>(FIBER_ANALYSIS_REPOSITORY),
        )
    }

    val properties by lazy { inject<TemplateProperties>() }
    val animalRepository by lazy { inject<Repository<Animal>>(ANIMAL_REPOSITORY) }
    val query by lazy { inject<ShowAnimalPage>() }

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
