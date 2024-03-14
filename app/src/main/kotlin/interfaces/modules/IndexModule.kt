package be.alpago.website.interfaces.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowIndexPageQuery
import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.inject
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.register
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

const val INDEX_ARTICLE_REPOSITORY = "pages/index/articles"
const val INDEX_GUILD_REPOSITORY = "pages/index/guilds"
const val INDEX_NEWS_REPOSITORY = "pages/index/news"
const val INDEX_TRAININGS_REPOSITORY = "pages/index/trainings"

private const val INDEX_ARTICLE_COLLECTION = "pages/index/article"
private const val INDEX_GUILD_COLLECTION = "pages/index/guilds"
private const val INDEX_NEWS_COLLECTION = "pages/index/news"
private const val INDEX_TRAININGS_COLLECTION = "pages/index/trainings"

fun Application.indexModule() {
    register<Repository<Article>>(INDEX_ARTICLE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER),
            )
        )
    }

    register<Repository<Highlight>>(INDEX_GUILD_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_GUILD_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER),
            )
        )
    }

    register<Repository<Highlight>>(INDEX_NEWS_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_NEWS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER),
            )
        )
    }

    register<Repository<ImageMetadata>>(INDEX_TRAININGS_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_TRAININGS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER),
            )
        )
    }

    register<ShowIndexPage> {
        ShowIndexPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            articleRepository = inject<Repository<Article>>(INDEX_ARTICLE_REPOSITORY),
            guildRepository = inject<Repository<Highlight>>(INDEX_GUILD_REPOSITORY),
            newsRepository = inject<Repository<Highlight>>(INDEX_NEWS_REPOSITORY),
            trainingRepository = inject<Repository<ImageMetadata>>(INDEX_TRAININGS_REPOSITORY),
        )
    }
}
