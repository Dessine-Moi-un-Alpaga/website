package be.alpago.website.modules.fiber

import be.alpago.website.domain.fiber.FiberAnalysis
import be.alpago.website.domain.fiber.FirestoreFiberAnalysisTransformer
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.libs.repository.FirestoreAggregateTransformer
import be.alpago.website.libs.repository.Repository
import be.alpago.website.libs.repository.RestFirestoreRepository
import be.alpago.website.pages.fiber.fiberAnalysisRoutes
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val FIBER_ANALYSIS_REPOSITORY = "fiberAnalyses"
const val FIBER_ANALYSIS_TRANSFORMER = "fiberAnalyses"

private const val FIBER_ANALYSIS_COLLECTION = "fiberAnalyses"

private fun fiberAnalysisModule() = module {
    single<FirestoreAggregateTransformer<FiberAnalysis>>(
        named(FIBER_ANALYSIS_TRANSFORMER)
    ) {
        FirestoreFiberAnalysisTransformer()
    }

    single<Repository<FiberAnalysis>>(
        named(FIBER_ANALYSIS_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingRepository(
            RestFirestoreRepository(
                client = client,
                collection = FIBER_ANALYSIS_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreFiberAnalysisTransformer(),
                url = environment.firestoreUrl,
            )
        )
    }
}

fun Application.fiberAnalyses() {
    koin {
        modules(
            fiberAnalysisModule()
        )
    }

    fiberAnalysisRoutes()
}
