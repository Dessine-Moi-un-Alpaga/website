package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowFactsheetArticle
import be.alpago.website.application.usecases.ShowFactsheetHighlights
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.persistence.Repository
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

suspend fun Application.factsheetRoutes() {
    val properties: TemplateProperties by dependencies
    val query: ShowFactsheetPage by dependencies

    routing {
        get("/factsheets.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository =  dependencies.resolve<Repository<Article>>(ShowFactsheetArticle::class.simpleName)
    managementRoutes("/api/factsheets/article", articleRepository)

    val factsheetRepository = dependencies.resolve<Repository<Highlight>>(ShowFactsheetHighlights::class.simpleName)
    managementRoutes("/api/factsheets/factsheets", factsheetRepository)
}
