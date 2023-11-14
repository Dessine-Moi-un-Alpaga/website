package be.alpago.website.pages.fiber

import be.alpago.website.domain.fiber.FiberAnalysis
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.modules.fiber.FIBER_ANALYSIS_REPOSITORY
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.fiberAnalysisRoutes() {
    val fiberAnalysisRepository by inject<Repository<FiberAnalysis>>(
        named(FIBER_ANALYSIS_REPOSITORY)
    )

    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
