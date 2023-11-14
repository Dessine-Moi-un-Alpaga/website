package be.alpago.website.pages.factsheet

import be.alpago.website.domain.article.Article
import be.alpago.website.domain.highlight.Highlight
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.modules.factsheet.FACTSHEET_ARTICLE_REPOSITORY
import be.alpago.website.modules.factsheet.FACTSHEET_HIGHLIGHT_REPOSITORY
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.factsheetRoutes() {
    routing {
        val environment by inject<Environment>()
        val pageModelFactory by inject<FactsheetPageModelFactory>()

        get("/factsheets.html") {
            val pageModel = pageModelFactory.create()
            val template = LayoutTemplate(environment, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by inject<Repository<Article>>(
        named(FACTSHEET_ARTICLE_REPOSITORY)
    )
    managementRoutes("/api/factsheets/article", articleRepository)

    val factsheetRepository by inject<Repository<Highlight>>(
        named(FACTSHEET_HIGHLIGHT_REPOSITORY)
    )
    managementRoutes("/api/factsheets/factsheets", factsheetRepository)
}
