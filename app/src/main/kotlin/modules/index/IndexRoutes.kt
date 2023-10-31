package be.alpago.website.modules.index

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.highlight.Highlight
import be.alpago.website.modules.image.ImageMetadata
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

private fun Application.indexRoute() {
    routing {
        val environment by inject<Environment>()
        val indexPageModelFactory by inject<IndexPageModelFactory>()

        get(Regex("/(index.html)?")) {
            val pageModel = indexPageModelFactory.create()
            val template = LayoutTemplate(environment, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }
}

private fun Application.articleManagementRoute() {
    val articleRepository by inject<CrudRepository<Article>>(
        named(IndexRepositories.article)
    )
    managementRoutes("/api/index/article", articleRepository)
}

private fun Application.guildManagementRoute() {
    val guildRepository by inject<CrudRepository<Highlight>>(
        named(IndexRepositories.guilds)
    )
    managementRoutes("/api/index/guilds", guildRepository)
}

private fun Application.newsManagementRoute() {
    val newsRepository by inject<CrudRepository<Highlight>>(
        named(IndexRepositories.news)
    )
    managementRoutes("/api/index/news", newsRepository)
}

private fun Application.trainingManagementRoute() {
    val trainingRepository by inject<CrudRepository<ImageMetadata>>(
        named(IndexRepositories.trainings)
    )
    managementRoutes("/api/index/trainings", trainingRepository)
}

fun Application.indexRoutes() {
    indexRoute()
    articleManagementRoute()
    guildManagementRoute()
    newsManagementRoute()
    trainingManagementRoute()
}
