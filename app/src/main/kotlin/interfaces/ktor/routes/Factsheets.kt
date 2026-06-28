package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.application.usecases.ShowFactsheetArticle
import be.alpago.website.application.usecases.ShowFactsheetHighlights
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.persistence.Repository
import be.alpago.website.libs.ktor.routes.managementRoutes
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Registers the HTTP endpoints related to factsheets:
 *
 * - `GET /factsheets.html`: returns the factsheet page itself
 * - [Management Routes][managementRoutes] for the factsheet page's [Article]:
 *     - `DELETE /api/factsheets/article`
 *     - `DELETE /api/factsheets/article/{id}`
 *     - `GET /api/factsheets/article`
 *     - `GET /api/factsheets/article/{id}`
 *     - `PUT /api/factsheets/article`
 * - [Management Routes][managementRoutes] for the factsheet [Highlight]s:
 *     - `DELETE /api/factsheets/factsheets`
 *     - `DELETE /api/factsheets/factsheets/{id}`
 *     - `GET /api/factsheets/factsheets`
 *     - `GET /api/factsheets/factsheets/{id}`
 *     - `PUT /api/factsheets/factsheets`
 */
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
