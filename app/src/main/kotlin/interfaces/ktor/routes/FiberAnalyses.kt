package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ManageFiberAnalyses
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application

fun Application.fiberAnalyses() {
    val fiberAnalysisRepository by lazy { inject<Repository<FiberAnalysis>>(ManageFiberAnalyses::class) }

    managementRoutes("/api/fiberAnalyses", fiberAnalysisRepository)
}
