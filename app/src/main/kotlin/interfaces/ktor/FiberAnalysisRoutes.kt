package be.alpago.website.interfaces.ktor

import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.interfaces.koin.FIBER_ANALYSIS_REPOSITORY
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.fiberAnalysisRoutes() {
    val fiberAnalysisRepository by inject<Repository<FiberAnalysis>>(
        named(FIBER_ANALYSIS_REPOSITORY)
    )

    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
