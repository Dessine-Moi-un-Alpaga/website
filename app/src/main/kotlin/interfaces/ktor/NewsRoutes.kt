package be.alpago.website.interfaces.ktor

import be.alpago.website.application.NewsPageModelFactory
import be.alpago.website.domain.Article
import be.alpago.website.interfaces.koin.NEWS_ARTICLE_REPOSITORY
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

fun Application.newsRoutes() {
    routing {
        val properties by inject<TemplateProperties>()
        val pageModelFactory by inject<NewsPageModelFactory>()

        get("/news.html") {
            val pageModel = pageModelFactory.create()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by inject<Repository<Article>>(
        named(NEWS_ARTICLE_REPOSITORY)
    )
    managementRoutes("/api/news/articles", articleRepository)
}
