package be.alpago.website.modules.animal

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.RestFirestoreCrudRepository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

private fun animalModule() = module {
    single<CrudRepository<Animal>>(
        named(AnimalRepositories.animal)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = ANIMAL_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreAnimalTransformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<CrudRepository<FiberAnalysis>>(
        named(AnimalRepositories.fiberAnalyses)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = FIBER_ANALYSIS_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreFiberAnalysisTransformer,
                url = environment.firestoreUrl,
            )
        )
    }
}

fun Application.animals() {
    koin {
        modules(
            animalModule()
        )
    }

    animalRoutes()
}
