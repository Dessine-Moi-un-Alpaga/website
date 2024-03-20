package be.alpago.website.adapters.firestore

import be.alpago.website.application.queries.ANIMAL_REPOSITORY
import be.alpago.website.application.queries.FACTSHEET_ARTICLE_REPOSITORY
import be.alpago.website.application.queries.FACTSHEET_HIGHLIGHT_REPOSITORY
import be.alpago.website.application.queries.INDEX_ARTICLE_REPOSITORY
import be.alpago.website.application.queries.INDEX_GUILD_REPOSITORY
import be.alpago.website.application.queries.INDEX_NEWS_REPOSITORY
import be.alpago.website.application.queries.INDEX_TRAININGS_REPOSITORY
import be.alpago.website.application.queries.NEWS_ARTICLE_REPOSITORY
import be.alpago.website.application.queries.PHOTO_GALLERY_IMAGE_REPOSITORY
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.domain.ports.FIBER_ANALYSIS_REPOSITORY
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

private const val ARTICLE_TRANSFORMER = "articles"
private const val FIBER_ANALYSIS_TRANSFORMER = "fiberAnalyses"
private const val HIGHLIGHT_TRANSFORMER = "highlights"
private const val IMAGE_METADATA_TRANSFORMER = "imageMetadata"

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
        createHttpClient().also {
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
    register<Repository<Animal>>(ANIMAL_REPOSITORY) {
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
    register<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER) {
        FirestoreArticleTransformer()
    }
}

private fun fiberAnalyses() {
    register<FirestoreAggregateTransformer<FiberAnalysis>>(FIBER_ANALYSIS_TRANSFORMER) {
        FirestoreFiberAnalysisTransformer()
    }

    register<Repository<FiberAnalysis>>(FIBER_ANALYSIS_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FIBER_ANALYSIS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<FiberAnalysis>>(FIBER_ANALYSIS_TRANSFORMER),
            )
        )
    }
}

private fun highlights() {
    register<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER) {
        FirestoreHighlightTransformer()
    }
}

private fun imageMetadata() {
    register<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER) {
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
    register<Repository<Article>>(INDEX_ARTICLE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER),
            )
        )
    }
}

private fun indexNewsHighlights() {
    register<Repository<Highlight>>(INDEX_NEWS_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_NEWS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER),
            )
        )
    }
}

private fun indexTrainingImages() {
    register<Repository<ImageMetadata>>(INDEX_TRAININGS_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_TRAININGS_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER),
            )
        )
    }
}

private fun indexGuildHighlights() {
    register<Repository<Highlight>>(INDEX_GUILD_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = INDEX_GUILD_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER),
            )
        )
    }
}

private fun newsPage() {
    register<Repository<Article>>(NEWS_ARTICLE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = NEWS_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER),
            )
        )
    }
}

private fun factsheetPage() {
    factsheetArticle()
    factsheetHighlight()
}

private fun factsheetArticle() {
    register<Repository<Article>>(FACTSHEET_ARTICLE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FACTSHEET_ARTICLE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER),
            )
        )
    }
}

private fun factsheetHighlight() {
    register<Repository<Highlight>>(FACTSHEET_HIGHLIGHT_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = FACTSHEET_HIGHLIGHT_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER),
            )
        )
    }
}

private fun photoGalleryPage() {
    register<Repository<ImageMetadata>>(PHOTO_GALLERY_IMAGE_REPOSITORY) {
        CachingRepository(
            FirestoreRepository(
                client = inject<HttpClient>(),
                collection = PHOTO_GALLERY_IMAGE_COLLECTION,
                properties = inject<FirestoreProperties>(),
                transformer = inject<FirestoreAggregateTransformer<ImageMetadata>>(IMAGE_METADATA_TRANSFORMER),
            )
        )
    }
}
