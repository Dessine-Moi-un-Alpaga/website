package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.queries.INDEX_ARTICLE_REPOSITORY
import be.alpago.website.application.queries.INDEX_GUILD_REPOSITORY
import be.alpago.website.application.queries.INDEX_NEWS_REPOSITORY
import be.alpago.website.application.queries.INDEX_TRAININGS_REPOSITORY
import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.index() {
    val properties by lazy { inject<TemplateProperties>() }
    val query by lazy { inject<ShowIndexPage>() }

    routing {
        get(Regex("/(index.html)?")) {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by lazy { inject<Repository<Article>>(INDEX_ARTICLE_REPOSITORY) }
    managementRoutes("/api/index/article", articleRepository)

    val guildRepository by lazy { inject<Repository<Highlight>>(INDEX_GUILD_REPOSITORY) }
    managementRoutes("/api/index/guilds", guildRepository)

    val newsRepository by lazy { inject<Repository<Highlight>>(INDEX_NEWS_REPOSITORY) }
    managementRoutes("/api/index/news", newsRepository)

    val trainingRepository by lazy { inject<Repository<ImageMetadata>>(INDEX_TRAININGS_REPOSITORY) }
    managementRoutes("/api/index/trainings", trainingRepository)
}
