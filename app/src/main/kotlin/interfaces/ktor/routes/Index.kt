package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowIndexArticle
import be.alpago.website.application.usecases.ShowIndexGuildHighlights
import be.alpago.website.application.usecases.ShowIndexNewsHighlights
import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.application.usecases.ShowIndexTrainingsPhotoGallery
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.persistence.Repository
import be.alpago.website.libs.ktor.routes.managementRoutes
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

suspend fun Application.indexRoutes() {
    val properties: TemplateProperties by dependencies
    val query: ShowIndexPage by dependencies

    routing {
        get(Regex("/(index.html)?")) {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository = dependencies.resolve<Repository<Article>>(ShowIndexArticle::class.simpleName)
    managementRoutes("/api/index/article", articleRepository)

    val guildRepository = dependencies.resolve<Repository<Highlight>>(ShowIndexGuildHighlights::class.simpleName)
    managementRoutes("/api/index/guilds", guildRepository)

    val newsRepository = dependencies.resolve<Repository<Highlight>>(ShowIndexNewsHighlights::class.simpleName)
    managementRoutes("/api/index/news", newsRepository)

    val trainingRepository = dependencies.resolve<Repository<ImageMetadata>>(ShowIndexTrainingsPhotoGallery::class.simpleName)
    managementRoutes("/api/index/trainings", trainingRepository)
}
