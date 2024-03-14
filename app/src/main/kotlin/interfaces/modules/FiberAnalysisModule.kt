package be.alpago.website.interfaces.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreFiberAnalysisTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.inject
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.register
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

const val FIBER_ANALYSIS_REPOSITORY = "fiberAnalyses"
const val FIBER_ANALYSIS_TRANSFORMER = "fiberAnalyses"

private const val FIBER_ANALYSIS_COLLECTION = "fiberAnalyses"

fun Application.fiberAnalysisModule() {
    register<FirestoreAggregateTransformer<FiberAnalysis>>(FIBER_ANALYSIS_TRANSFORMER) {
        FirestoreFiberAnalysisTransformer()
    }

    register<Repository<FiberAnalysis>>(FIBER_ANALYSIS_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FIBER_ANALYSIS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<FiberAnalysis>>(FIBER_ANALYSIS_TRANSFORMER),
            )
        )
    }
}
