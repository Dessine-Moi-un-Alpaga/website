package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreFiberAnalysisTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val FIBER_ANALYSIS_REPOSITORY = "fiberAnalyses"
const val FIBER_ANALYSIS_TRANSFORMER = "fiberAnalyses"

private const val FIBER_ANALYSIS_COLLECTION = "fiberAnalyses"

fun Application.fiberAnalysisModule() {
    koin {
        modules(
            module {
                single<FirestoreAggregateTransformer<FiberAnalysis>>(
                    named(FIBER_ANALYSIS_TRANSFORMER)
                ) {
                    FirestoreFiberAnalysisTransformer()
                }

                single<Repository<FiberAnalysis>>(
                    named(FIBER_ANALYSIS_REPOSITORY)
                ) {
                    CachingRepository(
                        FirestoreRepository(
                            client = get<HttpClient>(),
                            collection = FIBER_ANALYSIS_COLLECTION,
                            properties = get<FirestoreProperties>(),
                            transformer = get<FirestoreAggregateTransformer<FiberAnalysis>>(
                                named(FIBER_ANALYSIS_TRANSFORMER)
                            )
                        )
                    )
                }
            }
        )
    }
}
