package be.alpago.website.application.queries

import be.alpago.website.application.ArticleSectionModel
import be.alpago.website.application.HighlightsSectionModel
import be.alpago.website.application.Messages
import be.alpago.website.application.PageModel
import be.alpago.website.application.SectionColor
import be.alpago.website.application.SectionModel
import be.alpago.website.application.usecases.ShowFactsheetPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.Highlight
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

private val DESCRIPTION = "${Messages.factsheetPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.factsheets)}"

class ShowFactsheetPageQuery(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
    private val factsheetRepository: Repository<Highlight>,
) : ShowFactsheetPage {

    override suspend fun execute() = coroutineScope {
        val firstArticle = async { articleRepository.findAll().firstOrNull() }
        val factsheets = async { factsheetRepository.findAll() }
        val animals = async { animalRepository.findAll() }

        val sections = mutableListOf<SectionModel>()

        firstArticle.await()?.let { article ->
            sections.add(
                ArticleSectionModel(
                    article,
                    color = SectionColor.WHITE,
                    id = "article"
                )
            )
        }

        sections.add(
            HighlightsSectionModel(
                color = SectionColor.GREY,
                highlights = factsheets.await(),
                id = "highlights",
                sectionTitle = "${Messages.factsheetDownloadButton}",
            )
        )

        PageModel(
            animals = animals.await(),
            description = DESCRIPTION,
            sections = sections,
            title = TITLE,
        )
    }
}
