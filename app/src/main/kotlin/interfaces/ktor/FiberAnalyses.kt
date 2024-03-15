package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreFiberAnalysisTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.libs.domain.ports.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

const val FIBER_ANALYSIS_REPOSITORY = "fiberAnalyses"
const val FIBER_ANALYSIS_TRANSFORMER = "fiberAnalyses"

private const val FIBER_ANALYSIS_COLLECTION = "fiberAnalyses"

fun Application.fiberAnalyses() {
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

    val fiberAnalysisRepository by lazy { inject<Repository<FiberAnalysis>>(FIBER_ANALYSIS_REPOSITORY) }

    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
