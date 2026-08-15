package com.dessinemoiunalpaga.website.application.queries

import com.dessinemoiunalpaga.website.application.ArticleSectionModel
import com.dessinemoiunalpaga.website.application.PageModel
import com.dessinemoiunalpaga.website.application.SectionColor
import com.dessinemoiunalpaga.website.application.usecases.ShowNewsPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
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
