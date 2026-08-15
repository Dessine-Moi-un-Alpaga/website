package com.dessinemoiunalpaga.website.application.queries

import com.dessinemoiunalpaga.website.application.ArticleSectionModel
import com.dessinemoiunalpaga.website.application.HighlightsSectionModel
import com.dessinemoiunalpaga.website.application.PageModel
import com.dessinemoiunalpaga.website.application.PhotoGallerySectionModel
import com.dessinemoiunalpaga.website.application.SectionColor
import com.dessinemoiunalpaga.website.application.SectionModel
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.domain.Highlight
import com.dessinemoiunalpaga.website.domain.ImageMetadata
import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

private val DESCRIPTION = "${Messages.indexPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.presentation)}"

class ShowIndexPageQuery(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
    private val guildRepository: Repository<Highlight>,
    private val newsRepository: Repository<Highlight>,
    private val trainingRepository: Repository<ImageMetadata>,
) : ShowIndexPage {

    override suspend fun execute() = coroutineScope {
        val article = async { articleRepository.findAll().firstOrNull() }
        val news = async { newsRepository.findAll() }
        val trainings = async { trainingRepository.findAll() }
        val guilds = async { guildRepository.findAll() }
        val animals = async { animalRepository.findAll() }
        val sections = mutableListOf<SectionModel>()

        article.await()?.let {
            sections.add(
                ArticleSectionModel(
                    article = it,
                    color = SectionColor.WHITE,
                    id = "article",
                )
            )
        }

        sections.add(
            HighlightsSectionModel(
                color = SectionColor.GREY,
                highlights = news.await(),
                id = "news",
                sectionTitle = "${Messages.news}",
            )
        )

        sections.add(
            PhotoGallerySectionModel(
                color = SectionColor.RED,
                id = "trainings",
                images = trainings.await(),
                sectionTitle = "${Messages.trainings}",
            )
        )

        sections.add(
            HighlightsSectionModel(
                color = SectionColor.GREY,
                highlights = guilds.await(),
                id = "guilds",
                sectionTitle = "${Messages.guilds}",
            )
        )

        PageModel(
            title = TITLE,
            description = DESCRIPTION,
            animals = animals.await(),
            sections = sections,
        )
    }
}
