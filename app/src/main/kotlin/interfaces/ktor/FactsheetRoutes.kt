package be.alpago.website.interfaces.ktor

import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.inject
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.modules.FACTSHEET_ARTICLE_REPOSITORY
import be.alpago.website.interfaces.modules.FACTSHEET_HIGHLIGHT_REPOSITORY
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.factsheetRoutes() {
    val properties by lazy { inject<TemplateProperties>() }
    val query by lazy { inject<ShowFactsheetPage>() }

    routing {
        get("/factsheets.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by lazy { inject<Repository<Article>>(FACTSHEET_ARTICLE_REPOSITORY) }
    managementRoutes("/api/factsheets/article", articleRepository)

    val factsheetRepository by lazy { inject<Repository<Highlight>>(FACTSHEET_HIGHLIGHT_REPOSITORY) }
    managementRoutes("/api/factsheets/factsheets", factsheetRepository)
}
