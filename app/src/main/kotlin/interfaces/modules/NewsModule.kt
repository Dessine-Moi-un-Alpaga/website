package be.alpago.website.interfaces.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowNewsPageQuery
import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.inject
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.register
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

const val NEWS_ARTICLE_REPOSITORY = "pages/news/articles"

private const val NEWS_ARTICLE_COLLECTION = "pages/news/articles"

fun Application.newsModule() {
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
}
