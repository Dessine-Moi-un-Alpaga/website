package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowFactsheetArticle
import be.alpago.website.application.usecases.ShowFactsheetHighlights
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.factsheets() {
    val properties by lazy { inject<TemplateProperties>() }
    val query by lazy { inject<ShowFactsheetPage>() }

    routing {
        get("/factsheets.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by lazy { inject<Repository<Article>>(ShowFactsheetArticle::class) }
    managementRoutes("/api/factsheets/article", articleRepository)

    val factsheetRepository by lazy { inject<Repository<Highlight>>(ShowFactsheetHighlights::class) }
    managementRoutes("/api/factsheets/factsheets", factsheetRepository)
}
