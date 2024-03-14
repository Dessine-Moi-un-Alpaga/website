package be.alpago.website.modules

import be.alpago.website.adapters.firestore.FirestoreAnimalTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowAnimalPageQuery
import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.inject
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.register
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

const val ANIMAL_REPOSITORY = "animals"

private const val ANIMAL_COLLECTION = "animals"

fun Application.animalModule() {
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
}
