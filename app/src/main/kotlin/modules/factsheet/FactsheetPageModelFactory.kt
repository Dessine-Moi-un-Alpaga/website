package be.alpago.website.modules.factsheet

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.highlight.Highlight

private val DESCRIPTION = "${Messages.factsheetPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.factsheets)}"

class FactsheetPageModelFactory(
    private val animalRepository: CrudRepository<Animal>,
    private val articleRepository: CrudRepository<Article>,
    private val factsheetRepository: CrudRepository<Highlight>,
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
