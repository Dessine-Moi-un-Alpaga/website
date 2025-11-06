package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.fiberAnalysisRoutes() {
    val fiberAnalysisRepository: Repository<FiberAnalysis> by dependencies

    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
