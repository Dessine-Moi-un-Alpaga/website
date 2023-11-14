package be.alpago.website.pages.index

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.domain.article.Article
import be.alpago.website.domain.highlight.Highlight
import be.alpago.website.domain.image.ImageMetadata
import be.alpago.website.modules.index.INDEX_ARTICLE_REPOSITORY
import be.alpago.website.modules.index.INDEX_GUILD_REPOSITORY
import be.alpago.website.modules.index.INDEX_NEWS_REPOSITORY
import be.alpago.website.modules.index.INDEX_TRAININGS_REPOSITORY
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
    val articleRepository by inject<Repository<Article>>(
        named(INDEX_ARTICLE_REPOSITORY)
    )
    managementRoutes("/api/index/article", articleRepository)
}

private fun Application.guildManagementRoute() {
    val guildRepository by inject<Repository<Highlight>>(
        named(INDEX_GUILD_REPOSITORY)
    )
    managementRoutes("/api/index/guilds", guildRepository)
}

private fun Application.newsManagementRoute() {
    val newsRepository by inject<Repository<Highlight>>(
        named(INDEX_NEWS_REPOSITORY)
    )
    managementRoutes("/api/index/news", newsRepository)
}

private fun Application.trainingManagementRoute() {
    val trainingRepository by inject<Repository<ImageMetadata>>(
        named(INDEX_TRAININGS_REPOSITORY)
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
