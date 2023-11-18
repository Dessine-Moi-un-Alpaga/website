package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreAnimalTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowAnimalPageQuery
import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val ANIMAL_REPOSITORY = "animals"
const val ANIMAL_TRANSFORMER = "animals"

private const val ANIMAL_COLLECTION = "animals"

fun Application.animalModule() {
    koin {
        modules(
            module {
                single<FirestoreAggregateTransformer<Animal>>(
                    named(ANIMAL_TRANSFORMER)
                ) {
                    FirestoreAnimalTransformer()
                }

                single<Repository<Animal>>(
                    named(ANIMAL_REPOSITORY)
                ) {
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    val transformer by inject<FirestoreAggregateTransformer<Animal>>(
                        named(ANIMAL_TRANSFORMER)
                    )
                    CachingRepository(
                        FirestoreRepository(
                            client,
                            collection = ANIMAL_COLLECTION,
                            properties,
                            transformer,
                        )
                    )
                }

                single<ShowAnimalPage> {
                    val animalRepository by inject<Repository<Animal>>(
                        named(ANIMAL_REPOSITORY)
                    )
                    val fiberAnalysisRepository by inject<Repository<FiberAnalysis>>(
                        named(FIBER_ANALYSIS_REPOSITORY)
                    )
                    ShowAnimalPageQuery(
                        animalRepository = animalRepository,
                        fiberAnalysisRepository = fiberAnalysisRepository,
                    )
                }
            }
        )
    }
}
