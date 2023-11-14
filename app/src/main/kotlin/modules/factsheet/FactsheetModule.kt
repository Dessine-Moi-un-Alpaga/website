package be.alpago.website.modules.factsheet

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.article.Article
import be.alpago.website.domain.highlight.Highlight
import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingRepository
import be.alpago.website.libs.firestore.FirestoreAggregateTransformer
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.firestore.FirestoreRepository
import be.alpago.website.modules.animal.ANIMAL_REPOSITORY
import be.alpago.website.modules.article.ARTICLE_TRANSFORMER
import be.alpago.website.modules.article.HIGHLIGHT_TRANSFORMER
import be.alpago.website.pages.factsheet.FactsheetPageModelFactory
import be.alpago.website.pages.factsheet.factsheetRoutes
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val FACTSHEET_ARTICLE_REPOSITORY = "factsheets/article"

const val FACTSHEET_HIGHLIGHT_REPOSITORY = "factsheets/highlights"

private const val FACTSHEET_ARTICLE_COLLECTION = "pages/factsheeets/article"

private const val FACTSHEET_HIGHLIGHT_COLLECTION = "pages/factsheets/highlights"

private fun factsheetModule() = module {
    single<Repository<Article>>(
        named(FACTSHEET_ARTICLE_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        val transformer by inject<FirestoreAggregateTransformer<Article>>(
            named(ARTICLE_TRANSFORMER)
        )
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = FACTSHEET_ARTICLE_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = transformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<Repository<Highlight>>(
        named(FACTSHEET_HIGHLIGHT_REPOSITORY)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        val transformer by inject<FirestoreAggregateTransformer<Highlight>>(
            named(HIGHLIGHT_TRANSFORMER)
        )
        CachingRepository(
            FirestoreRepository(
                client = client,
                collection = FACTSHEET_HIGHLIGHT_COLLECTION,
                environment = environment.name,
                project = environment.project,
                transformer = transformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<FactsheetPageModelFactory> {
        val animalRepository by inject<Repository<Animal>>(
            named(ANIMAL_REPOSITORY)
        )
        val articleRepository by inject<Repository<Article>>(
            named(FACTSHEET_ARTICLE_REPOSITORY)
        )
        val factsheetRepository by inject<Repository<Highlight>>(
            named(FACTSHEET_HIGHLIGHT_REPOSITORY)
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
