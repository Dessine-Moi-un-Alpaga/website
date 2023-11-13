package be.alpago.website.modules.animal

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.animal.FirestoreAnimalTransformer
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.libs.repository.FirestoreAggregateTransformer
import be.alpago.website.libs.repository.Repository
import be.alpago.website.libs.repository.FirestoreRepository
import be.alpago.website.pages.animal.animalRoutes
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val ANIMAL_REPOSITORY = "animals"
const val ANIMAL_TRANSFORMER = "animals"

private const val ANIMAL_COLLECTION = "animals"

private fun animalModule() = module {
    single<FirestoreAggregateTransformer<Animal>>(
        named(ANIMAL_TRANSFORMER)
    ) {
        FirestoreAnimalTransformer()
    }

    single<Repository<Animal>>(
        named(ANIMAL_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        val transformer by inject<FirestoreAggregateTransformer<Animal>>(named(ANIMAL_TRANSFORMER))
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = ANIMAL_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = transformer,
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
