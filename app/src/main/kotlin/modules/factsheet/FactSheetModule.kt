package be.alpago.website.modules.factsheet

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.FirestorePageCollection
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.article.FirestoreArticleRepository
import be.alpago.website.modules.highlight.FirestoreHighlightRepository
import be.alpago.website.modules.highlight.Highlight
import com.google.cloud.firestore.Firestore
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

private const val DOCUMENT = "factsheets"

fun factsheetModule() = module {
    single<CrudRepository<Article>>(
        named(FactsheetRepositories.article)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreArticleRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                DOCUMENT to "article"
            )
        )
    }

    single<CrudRepository<Highlight>>(
        named(FactsheetRepositories.factsheets)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreHighlightRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                DOCUMENT to "factsheets"
            )
        )
    }

    single<FactsheetPageModelFactory> {
        val animalRepository by inject<CrudRepository<Animal>>(
            named(AnimalRepositories.animal)
        )
        val articleRepository by inject<CrudRepository<Article>>(
            named(FactsheetRepositories.article)
        )
        val factsheetRepository by inject<CrudRepository<Highlight>>(
            named(FactsheetRepositories.factsheets)
        )
        FactsheetPageModelFactory(
            animalRepository = animalRepository,
            articleRepository = articleRepository,
            factsheetRepository = factsheetRepository,
        )
    }
}

fun Application.factsheets() {
    koin {
        modules(factsheetModule())
    }

    factsheetRoutes()
}
