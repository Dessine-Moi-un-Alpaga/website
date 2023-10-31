package be.alpago.website.modules.news

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.FirestorePageCollection
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.article.FirestoreArticleRepository
import com.google.cloud.firestore.Firestore
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

private const val DOCUMENT = "news"

fun newsModule() = module {
    single<CrudRepository<Article>>(
        named(NewsRepositories.articles)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreArticleRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                DOCUMENT to "articles"
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