package be.alpago.website.application.queries

import be.alpago.website.application.usecases.ManageAnimals
import be.alpago.website.application.usecases.ManageFiberAnalyses
import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.application.usecases.ShowFactsheetArticle
import be.alpago.website.application.usecases.ShowFactsheetHighlights
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.application.usecases.ShowIndexArticle
import be.alpago.website.application.usecases.ShowIndexGuildHighlights
import be.alpago.website.application.usecases.ShowIndexNewsHighlights
import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.application.usecases.ShowIndexTrainingsPhotoGallery
import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.di.register
import be.alpago.website.libs.domain.ports.Repository

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
            animalRepository = inject<Repository<Animal>>(ManageAnimals::class),
            fiberAnalysisRepository = inject<Repository<FiberAnalysis>>(ManageFiberAnalyses::class),
        )
    }
}

private fun showFactsheetPage() {
    register<ShowFactsheetPage> {
        ShowFactsheetPageQuery(
            animalRepository = inject<Repository<Animal>>(ManageAnimals::class),
            articleRepository = inject<Repository<Article>>(ShowFactsheetArticle::class),
            factsheetRepository = inject<Repository<Highlight>>(ShowFactsheetHighlights::class),
        )
    }
}

private fun showIndexPage() {
    register<ShowIndexPage> {
        ShowIndexPageQuery(
            animalRepository = inject<Repository<Animal>>(ManageAnimals::class),
            articleRepository = inject<Repository<Article>>(ShowIndexArticle::class),
            guildRepository = inject<Repository<Highlight>>(ShowIndexGuildHighlights::class),
            newsRepository = inject<Repository<Highlight>>(ShowIndexNewsHighlights::class),
            trainingRepository = inject<Repository<ImageMetadata>>(ShowIndexTrainingsPhotoGallery::class),
        )
    }
}

private fun showNewsPage() {
    register<ShowNewsPage> {
        ShowNewsPageQuery(
            animalRepository = inject<Repository<Animal>>(ManageAnimals::class),
            articleRepository = inject<Repository<Article>>(ShowNewsPage::class),
        )
    }
}

private fun showPhotoGalleryPage() {
    register<ShowPhotoGalleryPage> {
        ShowPhotoGalleryPageQuery(
            animalRepository = inject<Repository<Animal>>(ManageAnimals::class),
            imageRepository = inject<Repository<ImageMetadata>>(ShowPhotoGalleryPage::class),
        )
    }
}
