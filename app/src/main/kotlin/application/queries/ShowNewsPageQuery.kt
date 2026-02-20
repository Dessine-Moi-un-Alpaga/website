package be.alpago.website.application.queries

import be.alpago.website.application.ArticleSectionModel
import be.alpago.website.application.PageModel
import be.alpago.website.application.SectionColor
import be.alpago.website.application.usecases.ShowNewsPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.i18n.Messages
import be.alpago.website.libs.domain.ports.persistence.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

private val COLORS = arrayOf(SectionColor.WHITE, SectionColor.GREY)

class ShowNewsPageQuery(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
) : ShowNewsPage {

    override suspend fun execute() = coroutineScope {
        val animals = async { animalRepository.findAll() }
        val articles = async { articleRepository.findAll() }

        var index = 0

        PageModel(
            animals = animals.await(),
            description = "",
            title = "${Messages.dmua} :: ${capitalize(Messages.news)}",
            sections = articles.await().map { article ->
                val model = ArticleSectionModel(
                    article = article,
                    color = COLORS[index % 2],
                    id = "news-${index}"
                )
                index++
                model
            },
        )
    }
}
