package be.alpago.website.application

import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize

private val DESCRIPTION = "${Messages.indexPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.presentation)}"

class IndexPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
    private val guildRepository: Repository<Highlight>,
    private val newsRepository: Repository<Highlight>,
    private val trainingRepository: Repository<ImageMetadata>,
) {

    suspend fun create(): PageModel {
        val animals = animalRepository.findAll()
        val articles = articleRepository.findAll()
        val guilds = guildRepository.findAll()
        val news = newsRepository.findAll()
        val trainings = trainingRepository.findAll()
        val sections = mutableListOf<SectionModel>()

        if (articles.isNotEmpty()) {
            sections.add(
                ArticleSectionModel(
                    color = SectionColor.WHITE,
                    article = articles.first(),
                )
            )
        }

        sections.add(
            HighlightsSectionModel(
                color = SectionColor.GREY,
                highlights = news,
                sectionTitle = "${Messages.news}",
            )
        )

        sections.add(
            PhotoGallerySectionModel(
                images = trainings,
                color = SectionColor.RED,
                sectionTitle = "${Messages.trainings}"
            )
        )

        sections.add(
            HighlightsSectionModel(
                color = SectionColor.GREY,
                highlights = guilds,
                sectionTitle = "${Messages.guilds}",
            )
        )

        return PageModel(
            title = TITLE,
            description = DESCRIPTION,
            animals = animals,
            sections = sections,
        )
    }
}
