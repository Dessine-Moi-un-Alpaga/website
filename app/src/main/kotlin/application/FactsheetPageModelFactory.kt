package be.alpago.website.application

import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize

private val DESCRIPTION = "${Messages.factsheetPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.factsheets)}"

class FactsheetPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
    private val factsheetRepository: Repository<Highlight>,
) {

    suspend fun create(): PageModel {
        val animals = animalRepository.findAll()
        val articles = articleRepository.findAll()
        val factsheets = factsheetRepository.findAll()
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
                sectionTitle = "${Messages.factsheetDownloadButton}",
                highlights = factsheets,
            )
        )

        return PageModel(
            animals = animals,
            description = DESCRIPTION,
            sections = sections,
            title = TITLE,
        )
    }
}
