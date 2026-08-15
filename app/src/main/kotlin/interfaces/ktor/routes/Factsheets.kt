package com.dessinemoiunalpaga.website.interfaces.ktor.routes

import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetArticle
import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetPage
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.domain.Highlight
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.LayoutTemplate
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.ktor.routes.managementRoutes
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
