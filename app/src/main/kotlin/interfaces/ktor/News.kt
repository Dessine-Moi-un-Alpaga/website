package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowNewsPageQuery
import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
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

const val NEWS_ARTICLE_REPOSITORY = "pages/news/articles"

private const val NEWS_ARTICLE_COLLECTION = "pages/news/articles"

fun Application.news() {
    register<Repository<Article>>(NEWS_ARTICLE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = NEWS_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER),
            )
        )
    }

    register<ShowNewsPage> {
        ShowNewsPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            articleRepository = inject<Repository<Article>>(NEWS_ARTICLE_REPOSITORY),
        )
    }

    val properties by lazy { inject<TemplateProperties>() }
    val query by lazy { inject<ShowNewsPage>() }

    routing {
        get("/news.html") {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository by lazy { inject<Repository<Article>>(NEWS_ARTICLE_REPOSITORY) }
    managementRoutes("/api/news/articles", articleRepository)
}
