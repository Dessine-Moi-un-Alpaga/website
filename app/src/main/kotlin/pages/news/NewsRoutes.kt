package be.alpago.website.pages.news

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.repository.Repository
import be.alpago.website.domain.article.Article
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject


fun Application.newsRoutes() {
    routing {
        val environment by inject<Environment>()
        val pageModelFactory by inject<NewsPageModelFactory>()

        get("/news.html") {
            val pageModel = pageModelFactory.create()
            val template = LayoutTemplate(environment, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by inject<Repository<Article>>(
        named(NEWS_ARTICLE_REPOSITORY)
    )
    managementRoutes("/api/news/articles", articleRepository)
}
