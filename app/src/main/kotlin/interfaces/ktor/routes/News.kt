package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.domain.Article
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

suspend fun Application.newsRoutes() {
    val properties: TemplateProperties by dependencies
    val query: ShowNewsPage by dependencies

    routing {
        get("/news.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository = dependencies.resolve<Repository<Article>>(ShowNewsPage::class.simpleName)
    managementRoutes("/api/news/articles", articleRepository)
}
