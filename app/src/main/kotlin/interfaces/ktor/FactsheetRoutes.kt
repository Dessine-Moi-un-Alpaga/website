package be.alpago.website.interfaces.ktor

import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.koin.FACTSHEET_ARTICLE_REPOSITORY
import be.alpago.website.interfaces.koin.FACTSHEET_HIGHLIGHT_REPOSITORY
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.factsheetRoutes() {
    routing {
        val properties by inject<TemplateProperties>()
        val query by inject<ShowFactsheetPage>()

        get("/factsheets.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
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
