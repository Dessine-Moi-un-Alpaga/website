package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreArticleTransformer
import be.alpago.website.adapters.firestore.FirestoreHighlightTransformer
import be.alpago.website.adapters.firestore.FirestoreImageMetadataTransformer
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
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    CachingRepository(
                        FirestoreRepository(
                            client,
                            collection = INDEX_ARTICLE_COLLECTION,
                            properties,
                            transformer = FirestoreArticleTransformer(),
                        )
                    )
                }

                single<Repository<Highlight>>(
                    named(INDEX_GUILD_REPOSITORY)
                ) {
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    CachingRepository(
                        FirestoreRepository(
                            client,
                            collection = INDEX_GUILD_COLLECTION,
                            properties,
                            transformer = FirestoreHighlightTransformer(),
                        )
                    )
                }

                single<Repository<Highlight>>(
                    named(INDEX_NEWS_REPOSITORY)
                ) {
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    CachingRepository(
                        FirestoreRepository(
                            client = client,
                            collection = INDEX_NEWS_COLLECTION,
                            properties,
                            transformer = FirestoreHighlightTransformer(),
                        )
                    )
                }

                single<Repository<ImageMetadata>>(
                    named(INDEX_TRAININGS_REPOSITORY)
                ) {
                    val client by inject<HttpClient>()
                    val properties by inject<FirestoreProperties>()
                    CachingRepository(
                        FirestoreRepository(
                            client,
                            collection = INDEX_TRAININGS_COLLECTION,
                            properties,
                            transformer = FirestoreImageMetadataTransformer(),
                        )
                    )
                }

                single<ShowIndexPage> {
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

                    ShowIndexPageQuery(
                        animalRepository,
                        articleRepository,
                        guildRepository,
                        newsRepository,
                        trainingRepository,
                    )
                }
            }
        )
    }
}
