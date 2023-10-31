package be.alpago.website.modules.news

import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.article.Article

private val COLORS = arrayOf(SectionColor.WHITE, SectionColor.GREY)

class NewsPageModelFactory(
    private val animalRepository: CrudRepository<Animal>,
    private val articleRepository: CrudRepository<Article>,
) {

    suspend fun create(): PageModel {
        val animals = animalRepository.findAll()
        val articles = articleRepository.findAll()
        val pageModel = PageModel(
            title = "Dessine-Moi un Alpaga :: Actualités",
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
