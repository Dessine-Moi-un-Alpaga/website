package be.alpago.website.application.queries

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
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.queries() {
    showAnimalPage()
    showFactsheetPage()
    showIndexPage()
    showNewsPage()
    showPhotoGalleryPage()
}

fun Application.showAnimalPage() {
    dependencies {
        provide<ShowAnimalPage> {
            ShowAnimalPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                fiberAnalysisRepository = resolve<Repository<FiberAnalysis>>(),
            )
        }
    }
}

fun Application.showFactsheetPage() {
    dependencies {
        provide<ShowFactsheetPage> {
            ShowFactsheetPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                articleRepository = resolve<Repository<Article>>(ShowFactsheetArticle::class.simpleName),
                factsheetRepository = resolve<Repository<Highlight>>(ShowFactsheetHighlights::class.simpleName),
            )
        }
    }
}

fun Application.showIndexPage() {
    dependencies {
        provide<ShowIndexPage> {
            ShowIndexPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                articleRepository = resolve<Repository<Article>>(ShowIndexArticle::class.simpleName),
                guildRepository = resolve<Repository<Highlight>>(ShowIndexGuildHighlights::class.simpleName),
                newsRepository = resolve<Repository<Highlight>>(ShowIndexNewsHighlights::class.simpleName),
                trainingRepository = resolve<Repository<ImageMetadata>>(ShowIndexTrainingsPhotoGallery::class.simpleName),
            )
        }
    }
}

fun Application.showNewsPage() {
    dependencies {
        provide<ShowNewsPage> {
            ShowNewsPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                articleRepository = resolve<Repository<Article>>(ShowNewsPage::class.simpleName),
            )
        }
    }
}

fun Application.showPhotoGalleryPage() {
    dependencies {
        provide<ShowPhotoGalleryPage> {
            ShowPhotoGalleryPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                imageRepository = resolve<Repository<ImageMetadata>>(ShowPhotoGalleryPage::class.simpleName),
            )
        }
    }
}
