package be.alpago.website.modules.news

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.article.Article
import be.alpago.website.domain.article.FirestoreArticleTransformer
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.libs.repository.Repository
import be.alpago.website.libs.repository.RestFirestoreRepository
import be.alpago.website.modules.animal.ANIMAL_REPOSITORY
import be.alpago.website.pages.news.NewsPageModelFactory
import be.alpago.website.pages.news.newsRoutes
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val NEWS_ARTICLE_REPOSITORY = "pages/news/articles"

private const val NEWS_ARTICLE_COLLECTION = "pages/news/articles"

private fun newsModule() = module {
    single<Repository<Article>>(
        named(NEWS_ARTICLE_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingRepository(
            RestFirestoreRepository(
                client = client,
                collection = NEWS_ARTICLE_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreArticleTransformer(),
                url = environment.firestoreUrl,
            )
        )
    }

    single<NewsPageModelFactory> {
        val animalRepository by inject<Repository<Animal>>(
            named(ANIMAL_REPOSITORY)
        )
        val articleRepository by inject<Repository<Article>>(
            named(NEWS_ARTICLE_REPOSITORY)
        )
        NewsPageModelFactory(
            animalRepository = animalRepository,
            articleRepository = articleRepository,
        )
    }
}

fun Application.news() {
    koin {
        modules(
            newsModule()
        )
    }

    newsRoutes()
}
