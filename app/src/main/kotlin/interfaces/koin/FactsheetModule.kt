package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowFactsheetPageQuery
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val FACTSHEET_ARTICLE_REPOSITORY = "factsheets/article"
const val FACTSHEET_HIGHLIGHT_REPOSITORY = "factsheets/highlights"

private const val FACTSHEET_ARTICLE_COLLECTION = "pages/factsheeets/article"
private const val FACTSHEET_HIGHLIGHT_COLLECTION = "pages/factsheets/highlights"

fun Application.factsheetModule() {
    koin {
        modules(
            module {
                single<Repository<Article>>(
                    named(FACTSHEET_ARTICLE_REPOSITORY)
                ) {
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    val transformer by inject<FirestoreAggregateTransformer<Article>>(
                        named(ARTICLE_TRANSFORMER)
                    )
                    CachingRepository(
                        FirestoreRepository(
                            client,
                            collection = FACTSHEET_ARTICLE_COLLECTION,
                            properties,
                            transformer,
                        )
                    )
                }

                single<Repository<Highlight>>(
                    named(FACTSHEET_HIGHLIGHT_REPOSITORY)
                ) {
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    val transformer by inject<FirestoreAggregateTransformer<Highlight>>(
                        named(HIGHLIGHT_TRANSFORMER)
                    )
                    CachingRepository(
                        FirestoreRepository(
                            client,
                            collection = FACTSHEET_HIGHLIGHT_COLLECTION,
                            properties,
                            transformer,
                        )
                    )
                }

                single<ShowFactsheetPage> {
                    val animalRepository by inject<Repository<Animal>>(
                        named(ANIMAL_REPOSITORY)
                    )
                    val articleRepository by inject<Repository<Article>>(
                        named(FACTSHEET_ARTICLE_REPOSITORY)
                    )
                    val factsheetRepository by inject<Repository<Highlight>>(
                        named(FACTSHEET_HIGHLIGHT_REPOSITORY)
                    )
                    ShowFactsheetPageQuery(
                        animalRepository,
                        articleRepository,
                        factsheetRepository,
                    )
                }
            }
        )
    }
}
