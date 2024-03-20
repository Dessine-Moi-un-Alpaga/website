package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.domain.ports.FIBER_ANALYSIS_REPOSITORY
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application

fun Application.fiberAnalyses() {
    val fiberAnalysisRepository by lazy { inject<Repository<FiberAnalysis>>(FIBER_ANALYSIS_REPOSITORY) }

    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
