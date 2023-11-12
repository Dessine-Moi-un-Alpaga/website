package be.alpago.website.pages.factsheet

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.article.Article
import be.alpago.website.domain.highlight.Highlight
import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.repository.Repository

private val DESCRIPTION = "${Messages.factsheetPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.factsheets)}"

class FactsheetPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
    private val factsheetRepository: Repository<Highlight>,
) {

    suspend fun create(): PageModel {
        val animals = animalRepository.findAll()
        val pageModel = PageModel(
            animals = animals,
            description = DESCRIPTION,
            title = TITLE,
        )

        val articles = articleRepository.findAll()

        if (articles.isNotEmpty()) {
            pageModel.createArticleSection(articles.first(), SectionColor.WHITE)
        }

        val factsheets = factsheetRepository.findAll()
        pageModel.createHighlightSection("${Messages.factsheetDownloadButton}", factsheets, SectionColor.GREY)

        return pageModel
    }
}
