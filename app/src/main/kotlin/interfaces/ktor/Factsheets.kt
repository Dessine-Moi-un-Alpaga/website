package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowFactsheetPageQuery
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.LayoutTemplate
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.libs.domain.ports.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

const val FACTSHEET_ARTICLE_REPOSITORY = "factsheets/article"
const val FACTSHEET_HIGHLIGHT_REPOSITORY = "factsheets/highlights"

private const val FACTSHEET_ARTICLE_COLLECTION = "pages/factsheeets/article"
private const val FACTSHEET_HIGHLIGHT_COLLECTION = "pages/factsheets/highlights"

fun Application.factsheets() {
    register<Repository<Article>>(FACTSHEET_ARTICLE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FACTSHEET_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER),
            )
        )
    }

    register<Repository<Highlight>>(FACTSHEET_HIGHLIGHT_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FACTSHEET_HIGHLIGHT_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER),
            )
        )
    }

    register<ShowFactsheetPage> {
        ShowFactsheetPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            articleRepository = inject<Repository<Article>>(FACTSHEET_ARTICLE_REPOSITORY),
            factsheetRepository = inject<Repository<Highlight>>(FACTSHEET_HIGHLIGHT_REPOSITORY),
        )
    }

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
