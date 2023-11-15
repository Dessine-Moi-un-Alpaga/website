package be.alpago.website.application

import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize

private val COLORS = arrayOf(SectionColor.WHITE, SectionColor.GREY)

class NewsPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
) {

    suspend fun create(): PageModel {
        val animals = animalRepository.findAll()
        val articles = articleRepository.findAll()
        var color = 0

        return PageModel(
            animals = animals,
            description = "",
            title = "${Messages.dmua} :: ${capitalize(Messages.news)}",
            sections = articles.map { article ->
                val model = ArticleSectionModel(
                    article = article,
                    color = COLORS[color++],
                )
                color %= 2
                model
            },
        )
    }
}
