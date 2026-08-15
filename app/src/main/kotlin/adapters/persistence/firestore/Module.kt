package com.dessinemoiunalpaga.website.adapters.persistence.firestore

import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetArticle
import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexArticle
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexGuildHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexNewsHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexTrainingsPhotoGallery
import com.dessinemoiunalpaga.website.application.usecases.ShowNewsPage
import com.dessinemoiunalpaga.website.application.usecases.ShowPhotoGalleryPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.domain.FiberAnalysis
import com.dessinemoiunalpaga.website.domain.Highlight
import com.dessinemoiunalpaga.website.domain.ImageMetadata
import com.dessinemoiunalpaga.website.libs.adapters.persistence.CachingRepository
import com.dessinemoiunalpaga.website.libs.adapters.persistence.RetryingRepository
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.getEnvironmentVariable
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

internal fun Application.firestore() {
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

private fun Application.httpClient() {
    dependencies {
        provide<HttpClient> {
            firestoreHttpClient()
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

private fun Application.animalRepository() {
    dependencies {
        provide<Repository<Animal>> {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = ANIMAL_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = FirestoreAnimalTransformer(),
                    )
                )
            )
        }
    }
}

private fun Application.articleTransformer() {
    dependencies {
        provide<FirestoreAggregateTransformer<Article>> {
            FirestoreArticleTransformer()
        }
    }
}

private fun Application.fiberAnalysisRepository() {
    dependencies {
        provide<Repository<FiberAnalysis>> {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = FIBER_ANALYSIS_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = FirestoreFiberAnalysisTransformer(),
                    )
                )
            )
        }
    }
}

private fun Application.highlightTransformer() {
    dependencies {
        provide<FirestoreAggregateTransformer<Highlight>> {
            FirestoreHighlightTransformer()
        }
    }
}

private fun Application.imageMetadataTransformer() {
    dependencies {
        provide<FirestoreAggregateTransformer<ImageMetadata>> {
            FirestoreImageMetadataTransformer()
        }
    }
}

private fun Application.indexPageRepositories() {
    indexArticleRepository()
    indexNewsHighlightRepository()
    indexTrainingImageRepository()
    indexGuildHighlightRepository()
}

private fun Application.indexArticleRepository() {
    dependencies {
        provide<Repository<Article>>(ShowIndexArticle::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = INDEX_ARTICLE_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<Article>>(),
                    )
                )
            )
        }
    }

}

private fun Application.indexNewsHighlightRepository() {
    dependencies {
        provide<Repository<Highlight>>(ShowIndexNewsHighlights::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = INDEX_NEWS_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<Highlight>>(),
                    )
                )
            )
        }
    }
}

private fun Application.indexTrainingImageRepository() {
    dependencies {
        provide<Repository<ImageMetadata>>(ShowIndexTrainingsPhotoGallery::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = INDEX_TRAININGS_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<ImageMetadata>>(),
                    )
                )
            )
        }
    }
}

private fun Application.indexGuildHighlightRepository() {
    dependencies {
        provide<Repository<Highlight>>(ShowIndexGuildHighlights::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = INDEX_GUILD_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<Highlight>>(),
                    )
                )
            )
        }
    }
}

private fun Application.newsPageRepositories() {
    dependencies {
        provide<Repository<Article>>(ShowNewsPage::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = NEWS_ARTICLE_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<Article>>(),
                    )
                )
            )
        }
    }
}

private fun Application.factsheetPageRepositories() {
    factsheetArticleRepository()
    factsheetHighlightRepository()
}

private fun Application.factsheetArticleRepository() {
    dependencies {
        provide<Repository<Article>>(ShowFactsheetArticle::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = FACTSHEET_ARTICLE_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<Article>>(),
                    )
                )
            )
        }
    }
}

private fun Application.factsheetHighlightRepository() {
    dependencies {
        provide<Repository<Highlight>>(ShowFactsheetHighlights::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = FACTSHEET_HIGHLIGHT_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<Highlight>>(),
                    )
                )
            )
        }
    }
}

private fun Application.photoGalleryPageRepositories() {
    dependencies {
        provide<Repository<ImageMetadata>>(ShowPhotoGalleryPage::class.simpleName) {
            CachingRepository(
                RetryingRepository(
                    FirestoreRepository(
                        client = resolve<HttpClient>(),
                        collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                        properties = resolve<FirestoreProperties>(),
                        transformer = resolve<FirestoreAggregateTransformer<ImageMetadata>>(),
                    )
                )
            )
        }
    }
}
