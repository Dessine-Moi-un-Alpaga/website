package be.alpago.website.adapters.firestore

import be.alpago.website.application.usecases.ShowFactsheetArticle
import be.alpago.website.application.usecases.ShowFactsheetHighlights
import be.alpago.website.application.usecases.ShowIndexArticle
import be.alpago.website.application.usecases.ShowIndexGuildHighlights
import be.alpago.website.application.usecases.ShowIndexNewsHighlights
import be.alpago.website.application.usecases.ShowIndexTrainingsPhotoGallery
import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.domain.ports.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.getEnvironmentVariable
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

private const val DEFAULT_ENVIRONMENT_NAME = "local"
private const val DEFAULT_FIRESTORE_URL = "https://firestore.googleapis.com"

private const val ANIMAL_COLLECTION = "animals"
private const val FACTSHEET_ARTICLE_COLLECTION = "pages/factsheeets/article"
private const val FACTSHEET_HIGHLIGHT_COLLECTION = "pages/factsheets/highlights"
private const val FIBER_ANALYSIS_COLLECTION = "fiberAnalyses"
private const val INDEX_ARTICLE_COLLECTION = "pages/index/article"
private const val INDEX_GUILD_COLLECTION = "pages/index/guilds"
private const val INDEX_NEWS_COLLECTION = "pages/index/news"
private const val INDEX_TRAININGS_COLLECTION = "pages/index/trainings"
private const val NEWS_ARTICLE_COLLECTION = "pages/news/articles"
private const val PHOTO_GALLERY_IMAGE_COLLECTION = "pages/gallery/images"

fun Application.firestore() {
    httpClient()
    firestoreProperties()

    animalRepository()
    articleTransformer()
    fiberAnalysisRepository()
    highlightTransformer()
    imageMetadataTransformer()

    indexPageRepositories()
    newsPageRepositories()
    factsheetPageRepositories()
    photoGalleryPageRepositories()
}

fun Application.httpClient() {
    dependencies {
        provide<HttpClient> {
            createHttpClient()
        }
    }
}

private fun Application.firestoreProperties() {
    dependencies {
        provide<FirestoreProperties> {
            FirestoreProperties(
                environmentName = getEnvironmentVariable("DMUA_ENVIRONMENT", DEFAULT_ENVIRONMENT_NAME),
                project = getEnvironmentVariable("DMUA_PROJECT"),
                url = getEnvironmentVariable("DMUA_FIRESTORE_URL", DEFAULT_FIRESTORE_URL),
            )
        }
    }
}

fun Application.animalRepository() {
    dependencies {
        provide<Repository<Animal>> {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = ANIMAL_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = FirestoreAnimalTransformer(),
                )
            )
        }
    }
}

fun Application.articleTransformer() {
    dependencies {
        provide<FirestoreAggregateTransformer<Article>> {
            FirestoreArticleTransformer()
        }
    }
}

fun Application.fiberAnalysisRepository() {
    dependencies {
        provide<Repository<FiberAnalysis>> {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = FIBER_ANALYSIS_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = FirestoreFiberAnalysisTransformer(),
                )
            )
        }
    }

}

fun Application.highlightTransformer() {
    dependencies {
        provide<FirestoreAggregateTransformer<Highlight>> {
            FirestoreHighlightTransformer()
        }
    }
}

fun Application.imageMetadataTransformer() {
    dependencies {
        provide<FirestoreAggregateTransformer<ImageMetadata>> {
            FirestoreImageMetadataTransformer()
        }
    }
}

fun Application.indexPageRepositories() {
    indexArticleRepository()
    indexNewsHighlightRepository()
    indexTrainingImageRepository()
    indexGuildHighlightRepository()
}

private fun Application.indexArticleRepository() {
    dependencies {
        provide<Repository<Article>>(ShowIndexArticle::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = INDEX_ARTICLE_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<Article>>(),
                )
            )
        }
    }

}

private fun Application.indexNewsHighlightRepository() {
    dependencies {
        provide<Repository<Highlight>>(ShowIndexNewsHighlights::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = INDEX_NEWS_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<Highlight>>(),
                )
            )
        }
    }
}

private fun Application.indexTrainingImageRepository() {
    dependencies {
        provide<Repository<ImageMetadata>>(ShowIndexTrainingsPhotoGallery::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = INDEX_TRAININGS_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<ImageMetadata>>(),
                )
            )
        }
    }
}

private fun Application.indexGuildHighlightRepository() {
    dependencies {
        provide<Repository<Highlight>>(ShowIndexGuildHighlights::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = INDEX_GUILD_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<Highlight>>(),
                )
            )
        }
    }
}

fun Application.newsPageRepositories() {
    dependencies {
        provide<Repository<Article>>(ShowNewsPage::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = NEWS_ARTICLE_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<Article>>(),
                )
            )
        }
    }
}

fun Application.factsheetPageRepositories() {
    factsheetArticleRepository()
    factsheetHighlightRepository()
}

private fun Application.factsheetArticleRepository() {
    dependencies {
        provide<Repository<Article>>(ShowFactsheetArticle::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = FACTSHEET_ARTICLE_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<Article>>(),
                )
            )
        }
    }
}

private fun Application.factsheetHighlightRepository() {
    dependencies {
        provide<Repository<Highlight>>(ShowFactsheetHighlights::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = FACTSHEET_HIGHLIGHT_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<Highlight>>(),
                )
            )
        }
    }
}

fun Application.photoGalleryPageRepositories() {
    dependencies {
        provide<Repository<ImageMetadata>>(ShowPhotoGalleryPage::class.simpleName) {
            CachingRepository(
                FirestoreRepository(
                    client = resolve<HttpClient>(),
                    collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                    properties = resolve<FirestoreProperties>(),
                    transformer = resolve<FirestoreAggregateTransformer<ImageMetadata>>(),
                )
            )
        }
    }
}
