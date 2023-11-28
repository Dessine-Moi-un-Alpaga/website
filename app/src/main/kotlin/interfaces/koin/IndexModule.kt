package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreProperties
import be.alpago.website.adapters.firestore.FirestoreRepository
import be.alpago.website.application.queries.ShowIndexPageQuery
import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.repository.CachingRepository
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

fun Application.indexModule() {
    koin {
        modules(
            module {
                single<Repository<Article>>(
                    named(INDEX_ARTICLE_REPOSITORY)
                ) {
                    CachingRepository(
                        FirestoreRepository(
                            client = get<HttpClient>(),
                            collection = INDEX_ARTICLE_COLLECTION,
                            properties = get<FirestoreProperties>(),
                            transformer = get<FirestoreAggregateTransformer<Article>>(
                                named(ARTICLE_TRANSFORMER)
                            )
                        )
                    )
                }

                single<Repository<Highlight>>(
                    named(INDEX_GUILD_REPOSITORY)
                ) {
                    CachingRepository(
                        FirestoreRepository(
                            client = get<HttpClient>(),
                            collection = INDEX_GUILD_COLLECTION,
                            properties = get<FirestoreProperties>(),
                            transformer = get<FirestoreAggregateTransformer<Highlight>>(
                                named(HIGHLIGHT_TRANSFORMER)
                            )
                        )
                    )
                }

                single<Repository<Highlight>>(
                    named(INDEX_NEWS_REPOSITORY)
                ) {
                    CachingRepository(
                        FirestoreRepository(
                            client = get<HttpClient>(),
                            collection = INDEX_NEWS_COLLECTION,
                            properties = get<FirestoreProperties>(),
                            transformer = get<FirestoreAggregateTransformer<Highlight>>(
                                named(HIGHLIGHT_TRANSFORMER)
                            )
                        )
                    )
                }

                single<Repository<ImageMetadata>>(
                    named(INDEX_TRAININGS_REPOSITORY)
                ) {
                    CachingRepository(
                        FirestoreRepository(
                            client = get<HttpClient>(),
                            collection = INDEX_TRAININGS_COLLECTION,
                            properties = get<FirestoreProperties>(),
                            transformer = get<FirestoreAggregateTransformer<ImageMetadata>>(
                                named(IMAGE_METADATA_TRANSFORMER)
                            )
                        )
                    )
                }

                single<ShowIndexPage> {
                    ShowIndexPageQuery(
                        animalRepository = get<Repository<Animal>>(
                            named(ANIMAL_REPOSITORY)
                        ),
                        articleRepository = get<Repository<Article>>(
                            named(INDEX_ARTICLE_REPOSITORY)
                        ),
                        guildRepository = get<Repository<Highlight>>(
                            named(INDEX_GUILD_REPOSITORY)
                        ),
                        newsRepository = get<Repository<Highlight>>(
                            named(INDEX_NEWS_REPOSITORY)
                        ),
                        trainingRepository = get<Repository<ImageMetadata>>(
                            named(INDEX_TRAININGS_REPOSITORY)
                        )
                    )
                }
            }
        )
    }
}
