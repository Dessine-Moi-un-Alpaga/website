package be.alpago.website.interfaces.ktor

import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.koin.INDEX_ARTICLE_REPOSITORY
import be.alpago.website.interfaces.koin.INDEX_GUILD_REPOSITORY
import be.alpago.website.interfaces.koin.INDEX_NEWS_REPOSITORY
import be.alpago.website.interfaces.koin.INDEX_TRAININGS_REPOSITORY
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

private fun Application.indexRoute() {
    routing {
        val properties by inject<TemplateProperties>()
        val query by inject<ShowIndexPage>()

        get(Regex("/(index.html)?")) {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
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
