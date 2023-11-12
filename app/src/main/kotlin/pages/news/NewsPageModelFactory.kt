package be.alpago.website.pages.news

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.repository.Repository
import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.article.Article

private val COLORS = arrayOf(SectionColor.WHITE, SectionColor.GREY)

class NewsPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
) {

    suspend fun create(): PageModel {
        val animals = animalRepository.findAll()
        val articles = articleRepository.findAll()
        val pageModel = PageModel(
            title = "${Messages.dmua} :: ${capitalize(Messages.news)}",
            description = "",
            animals = animals
        )

        var color = 0

        for (article in articles) {
            pageModel.createArticleSection(article, COLORS[color++])
            color %= 2
        }

        return pageModel
    }
}
