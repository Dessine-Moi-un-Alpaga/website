package be.alpago.website.modules.factsheet

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.managementRoutes
import be.alpago.website.libs.page.template.LayoutTemplate
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.highlight.Highlight
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

    val articleRepository by inject<CrudRepository<Article>>(
        named(FactsheetRepositories.article)
    )
    managementRoutes("/api/factsheets/article", articleRepository)

    val factsheetRepository by inject<CrudRepository<Highlight>>(
        named(FactsheetRepositories.factsheets)
    )
    managementRoutes("/api/factsheets/factsheets", factsheetRepository)
}
