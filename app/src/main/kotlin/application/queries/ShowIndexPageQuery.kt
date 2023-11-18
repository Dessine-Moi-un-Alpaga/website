package be.alpago.website.application.queries

import be.alpago.website.application.ArticleSectionModel
import be.alpago.website.application.HighlightsSectionModel
import be.alpago.website.application.Messages
import be.alpago.website.application.PageModel
import be.alpago.website.application.PhotoGallerySectionModel
import be.alpago.website.application.SectionColor
import be.alpago.website.application.SectionModel
import be.alpago.website.application.usecases.ShowIndexPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize
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
                )
            )
        }

        sections.add(
            HighlightsSectionModel(
                color = SectionColor.GREY,
                highlights = news.await(),
                sectionTitle = "${Messages.news}",
            )
        )

        sections.add(
            PhotoGallerySectionModel(
                images = trainings.await(),
                color = SectionColor.RED,
                sectionTitle = "${Messages.trainings}"
            )
        )

        sections.add(
            HighlightsSectionModel(
                color = SectionColor.GREY,
                highlights = guilds.await(),
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
