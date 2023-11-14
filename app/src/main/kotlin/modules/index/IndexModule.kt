package be.alpago.website.modules.index

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.article.Article
import be.alpago.website.domain.article.FirestoreArticleTransformer
import be.alpago.website.domain.highlight.FirestoreHighlightTransformer
import be.alpago.website.domain.highlight.Highlight
import be.alpago.website.domain.image.FirestoreImageMetadataTransformer
import be.alpago.website.domain.image.ImageMetadata
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.firestore.FirestoreRepository
import be.alpago.website.modules.animal.ANIMAL_REPOSITORY
import be.alpago.website.pages.index.IndexPageModelFactory
import be.alpago.website.pages.index.indexRoutes
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val INDEX_ARTICLE_REPOSITORY = "pages/index/articles"
const val INDEX_GUILD_REPOSITORY = "pages/index/guilds"
const val INDEX_NEWS_REPOSITORY = "pages/index/news"
const val INDEX_TRAININGS_REPOSITORY = "pages/index/trainings"

private const val INDEX_ARTICLE_COLLECTION = "pages/index/article"
private const val INDEX_GUILD_COLLECTION = "pages/index/guilds"
private const val INDEX_NEWS_COLLECTION = "pages/index/news"
private const val INDEX_TRAININGS_COLLECTION = "pages/index/trainings"

private fun indexModule() = module {
    single<Repository<Article>>(
        named(INDEX_ARTICLE_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = INDEX_ARTICLE_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreArticleTransformer(),
                url = environment.firestoreUrl,
            )
        )
    }

    single<Repository<Highlight>>(
        named(INDEX_GUILD_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = INDEX_GUILD_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreHighlightTransformer(),
                url = environment.firestoreUrl,
            )
        )
    }

    single<Repository<Highlight>>(
        named(INDEX_NEWS_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = INDEX_NEWS_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreHighlightTransformer(),
                url = environment.firestoreUrl,
            )
        )
    }

    single<Repository<ImageMetadata>>(
        named(INDEX_TRAININGS_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = INDEX_TRAININGS_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreImageMetadataTransformer(),
                url = environment.firestoreUrl,
            )
        )
    }

    single<IndexPageModelFactory> {
        val animalRepository by inject<Repository<Animal>>(
            named(ANIMAL_REPOSITORY)
        )
        val articleRepository by inject<Repository<Article>>(
            named(INDEX_ARTICLE_REPOSITORY)
        )
        val guildRepository by inject<Repository<Highlight>>(
            named(INDEX_GUILD_REPOSITORY)
        )
        val newsRepository by inject<Repository<Highlight>>(
            named(INDEX_NEWS_REPOSITORY)
        )
        val trainingRepository by inject<Repository<ImageMetadata>>(
            named(INDEX_TRAININGS_REPOSITORY)
        )

        IndexPageModelFactory(
            animalRepository = animalRepository,
            articleRepository = articleRepository,
            guildRepository = guildRepository,
            newsRepository= newsRepository,
            trainingRepository = trainingRepository,
        )
    }
}

fun Application.index() {
    koin {
        modules(indexModule())
    }

    indexRoutes()
}
