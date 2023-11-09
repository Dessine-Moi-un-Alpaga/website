package be.alpago.website.modules.factsheet

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.RestFirestoreCrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.article.FirestoreArticleTransformer
import be.alpago.website.modules.highlight.FirestoreHighlightTransformer
import be.alpago.website.modules.highlight.Highlight
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

object FactsheetRepositories {
    const val article = "factsheets/article"
    const val factsheets = "factsheets/factsheets"
}

fun factsheetModule() = module {
    single<CrudRepository<Article>>(
        named(FactsheetRepositories.article)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = FirestoreFactsheetRepositories.article,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreArticleTransformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<CrudRepository<Highlight>>(
        named(FactsheetRepositories.factsheets)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = FirestoreFactsheetRepositories.highlights,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreHighlightTransformer,
                url = environment.firestoreUrl,
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
