package be.alpago.website.interfaces.koin

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

private const val ANIMAL_COLLECTION = "animals"

fun Application.animalModule() {
    koin {
        modules(
            module {
                single<Repository<Animal>>(
                    named(ANIMAL_REPOSITORY)
                ) {
                    CachingRepository(
                        FirestoreRepository(
                            client = get<HttpClient>(),
                            collection = ANIMAL_COLLECTION,
                            properties = get<FirestoreProperties>(),
                            transformer = FirestoreAnimalTransformer()
                        )
                    )
                }

                single<ShowAnimalPage> {
                    ShowAnimalPageQuery(
                        animalRepository = get<Repository<Animal>>(
                            named(ANIMAL_REPOSITORY)
                        ),
                        fiberAnalysisRepository = get<Repository<FiberAnalysis>>(
                            named(FIBER_ANALYSIS_REPOSITORY)
                        )
                    )
                }
            }
        )
    }
}
