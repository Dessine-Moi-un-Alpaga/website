package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.inject
import be.alpago.website.modules.FIBER_ANALYSIS_REPOSITORY
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application

fun Application.fiberAnalysisRoutes() {
    val fiberAnalysisRepository by lazy { inject<Repository<FiberAnalysis>>(FIBER_ANALYSIS_REPOSITORY) }

    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
