package be.alpago.website.interfaces.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowFactsheetPageQuery
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.inject
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.register
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

const val FACTSHEET_ARTICLE_REPOSITORY = "factsheets/article"
const val FACTSHEET_HIGHLIGHT_REPOSITORY = "factsheets/highlights"

private const val FACTSHEET_ARTICLE_COLLECTION = "pages/factsheeets/article"
private const val FACTSHEET_HIGHLIGHT_COLLECTION = "pages/factsheets/highlights"

fun Application.factsheetModule() {
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
}
