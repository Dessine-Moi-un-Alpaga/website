package be.alpago.website.modules.news

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.RestFirestoreCrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.article.FirestoreArticleTransformer
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

private const val DOCUMENT = "news"

fun newsModule() = module {
    single<CrudRepository<Article>>(
        named(NewsRepositories.articles)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = NewsRepositories.articles,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreArticleTransformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<NewsPageModelFactory> {
        val animalRepository by inject<CrudRepository<Animal>>(
            named(AnimalRepositories.animal)
        )
        val articleRepository by inject<CrudRepository<Article>>(
            named(NewsRepositories.articles)
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
