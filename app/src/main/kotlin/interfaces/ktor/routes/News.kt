package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.domain.Article
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.news() {
    val properties by lazy { inject<TemplateProperties>() }
    val query by lazy { inject<ShowNewsPage>() }

    routing {
        get("/news.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by lazy { inject<Repository<Article>>(ShowNewsPage::class) }
    managementRoutes("/api/news/articles", articleRepository)
}
