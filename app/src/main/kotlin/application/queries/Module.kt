package com.dessinemoiunalpaga.website.application.queries

import com.dessinemoiunalpaga.website.application.usecases.ShowAnimalPage
import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetArticle
import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetPage
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexArticle
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexGuildHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexNewsHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexPage
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexTrainingsPhotoGallery
import com.dessinemoiunalpaga.website.application.usecases.ShowNewsPage
import com.dessinemoiunalpaga.website.application.usecases.ShowPhotoGalleryPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.domain.FiberAnalysis
import com.dessinemoiunalpaga.website.domain.Highlight
import com.dessinemoiunalpaga.website.domain.ImageMetadata
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

internal fun Application.queries() {
    showAnimalPage()
    showFactsheetPage()
    showIndexPage()
    showNewsPage()
    showPhotoGalleryPage()
}

private fun Application.showAnimalPage() {
    dependencies {
        provide<ShowAnimalPage> {
            ShowAnimalPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                fiberAnalysisRepository = resolve<Repository<FiberAnalysis>>(),
            )
        }
    }
}

private fun Application.showFactsheetPage() {
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

private fun Application.showIndexPage() {
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

private fun Application.showNewsPage() {
    dependencies {
        provide<ShowNewsPage> {
            ShowNewsPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                articleRepository = resolve<Repository<Article>>(ShowNewsPage::class.simpleName),
            )
        }
    }
}

private fun Application.showPhotoGalleryPage() {
    dependencies {
        provide<ShowPhotoGalleryPage> {
            ShowPhotoGalleryPageQuery(
                animalRepository = resolve<Repository<Animal>>(),
                imageRepository = resolve<Repository<ImageMetadata>>(ShowPhotoGalleryPage::class.simpleName),
            )
        }
    }
}
