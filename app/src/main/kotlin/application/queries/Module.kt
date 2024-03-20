package be.alpago.website.application.queries

import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.domain.ports.FIBER_ANALYSIS_REPOSITORY
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.di.register
import be.alpago.website.libs.domain.ports.Repository

const val ANIMAL_REPOSITORY = "animals"
const val FACTSHEET_ARTICLE_REPOSITORY = "factsheets/article"
const val FACTSHEET_HIGHLIGHT_REPOSITORY = "factsheets/highlights"
const val INDEX_ARTICLE_REPOSITORY = "pages/index/articles"
const val INDEX_GUILD_REPOSITORY = "pages/index/guilds"
const val INDEX_NEWS_REPOSITORY = "pages/index/news"
const val INDEX_TRAININGS_REPOSITORY = "pages/index/trainings"
const val NEWS_ARTICLE_REPOSITORY = "pages/news/articles"
const val PHOTO_GALLERY_IMAGE_REPOSITORY = "pages/gallery/images"

fun queries() {
    showAnimalPage()
    showFactsheetPage()
    showIndexPage()
    showNewsPage()
    showPhotoGalleryPage()
}

private fun showAnimalPage() {
    register<ShowAnimalPage> {
        ShowAnimalPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            fiberAnalysisRepository = inject<Repository<FiberAnalysis>>(FIBER_ANALYSIS_REPOSITORY),
        )
    }
}

private fun showFactsheetPage() {
    register<ShowFactsheetPage> {
        ShowFactsheetPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            articleRepository = inject<Repository<Article>>(FACTSHEET_ARTICLE_REPOSITORY),
            factsheetRepository = inject<Repository<Highlight>>(FACTSHEET_HIGHLIGHT_REPOSITORY),
        )
    }
}

private fun showIndexPage() {
    register<ShowIndexPage> {
        ShowIndexPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            articleRepository = inject<Repository<Article>>(INDEX_ARTICLE_REPOSITORY),
            guildRepository = inject<Repository<Highlight>>(INDEX_GUILD_REPOSITORY),
            newsRepository = inject<Repository<Highlight>>(INDEX_NEWS_REPOSITORY),
            trainingRepository = inject<Repository<ImageMetadata>>(INDEX_TRAININGS_REPOSITORY),
        )
    }
}

private fun showNewsPage() {
    register<ShowNewsPage> {
        ShowNewsPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            articleRepository = inject<Repository<Article>>(NEWS_ARTICLE_REPOSITORY),
        )
    }
}

private fun showPhotoGalleryPage() {
    register<ShowPhotoGalleryPage> {
        ShowPhotoGalleryPageQuery(
            animalRepository = inject<Repository<Animal>>(ANIMAL_REPOSITORY),
            imageRepository = inject<Repository<ImageMetadata>>(PHOTO_GALLERY_IMAGE_REPOSITORY),
        )
    }
}
