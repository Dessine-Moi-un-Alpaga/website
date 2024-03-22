package be.alpago.website.adapters.firestore

import be.alpago.website.application.usecases.ManageAnimals
import be.alpago.website.application.usecases.ManageFiberAnalyses
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
import be.alpago.website.interfaces.ktor.registerCloseable
import be.alpago.website.libs.di.getEnvironmentVariable
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.di.register
import be.alpago.website.libs.domain.ports.CachingRepository
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.client.HttpClient
import io.ktor.server.application.Application

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

    animals()
    articles()
    fiberAnalyses()
    highlights()
    imageMetadata()

    indexPage()
    newsPage()
    factsheetPage()
    photoGalleryPage()
}

private fun Application.httpClient() {
    register<HttpClient> {
        createNewHttpClient().also {
            registerCloseable(it)
        }
    }
}

private fun firestoreProperties() {
    register<FirestoreProperties> {
        FirestoreProperties(
            environmentName = getEnvironmentVariable("DMUA_ENVIRONMENT", DEFAULT_ENVIRONMENT_NAME),
            project = getEnvironmentVariable("DMUA_PROJECT"),
            url = getEnvironmentVariable("DMUA_FIRESTORE_URL", DEFAULT_FIRESTORE_URL),
        )
    }
}

private fun animals() {
    register<Repository<Animal>>(ManageAnimals::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = ANIMAL_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = FirestoreAnimalTransformer(),
            )
        )
    }
}

private fun articles() {
    register<FirestoreAggregateTransformer<Article>>(Article::class) {
        FirestoreArticleTransformer()
    }
}

private fun fiberAnalyses() {
    register<FirestoreAggregateTransformer<FiberAnalysis>>(FiberAnalysis::class) {
        FirestoreFiberAnalysisTransformer()
    }

    register<Repository<FiberAnalysis>>(ManageFiberAnalyses::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FIBER_ANALYSIS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<FiberAnalysis>>(FiberAnalysis::class),
            )
        )
    }
}

private fun highlights() {
    register<FirestoreAggregateTransformer<Highlight>>(Highlight::class) {
        FirestoreHighlightTransformer()
    }
}

private fun imageMetadata() {
    register<FirestoreAggregateTransformer<ImageMetadata>>(ImageMetadata::class) {
        FirestoreImageMetadataTransformer()
    }
}

private fun indexPage() {
    indexArticle()
    indexNewsHighlights()
    indexTrainingImages()
    indexGuildHighlights()
}

private fun indexArticle() {
    register<Repository<Article>>(ShowIndexArticle::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(Article::class),
            )
        )
    }
}

private fun indexNewsHighlights() {
    register<Repository<Highlight>>(ShowIndexNewsHighlights::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_NEWS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(Highlight::class),
            )
        )
    }
}

private fun indexTrainingImages() {
    register<Repository<ImageMetadata>>(ShowIndexTrainingsPhotoGallery::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_TRAININGS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<ImageMetadata>>(ImageMetadata::class),
            )
        )
    }
}

private fun indexGuildHighlights() {
    register<Repository<Highlight>>(ShowIndexGuildHighlights::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_GUILD_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(Highlight::class),
            )
        )
    }
}

private fun newsPage() {
    register<Repository<Article>>(ShowNewsPage::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = NEWS_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(Article::class),
            )
        )
    }
}

private fun factsheetPage() {
    factsheetArticle()
    factsheetHighlight()
}

private fun factsheetArticle() {
    register<Repository<Article>>(ShowFactsheetArticle::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FACTSHEET_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(Article::class),
            )
        )
    }
}

private fun factsheetHighlight() {
    register<Repository<Highlight>>(ShowFactsheetHighlights::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FACTSHEET_HIGHLIGHT_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(Highlight::class),
            )
        )
    }
}

private fun photoGalleryPage() {
    register<Repository<ImageMetadata>>(ShowPhotoGalleryPage::class) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<ImageMetadata>>(ImageMetadata::class),
            )
        )
    }
}
