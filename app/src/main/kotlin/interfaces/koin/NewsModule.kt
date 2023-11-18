package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreArticleTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowNewsPageQuery
import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val NEWS_ARTICLE_REPOSITORY = "pages/news/articles"

private const val NEWS_ARTICLE_COLLECTION = "pages/news/articles"

fun Application.newsModule() {
    koin {
        modules(
            module {
                single<Repository<Article>>(
                    named(NEWS_ARTICLE_REPOSITORY)
                ) {
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    CachingRepository(
                        FirestoreRepository(
                            client,
                            collection = NEWS_ARTICLE_COLLECTION,
                            properties,
                            transformer = FirestoreArticleTransformer(),
                        )
                    )
                }

                single<ShowNewsPage> {
                    val animalRepository by inject<Repository<Animal>>(
                        named(ANIMAL_REPOSITORY)
                    )
                    val articleRepository by inject<Repository<Article>>(
                        named(NEWS_ARTICLE_REPOSITORY)
                    )
                    ShowNewsPageQuery(
                        animalRepository,
                        articleRepository,
                    )
                }
            }
        )
    }
}
