package com.dessinemoiunalpaga.website.application.queries

import com.dessinemoiunalpaga.website.application.ArticleSectionModel
import com.dessinemoiunalpaga.website.application.HighlightsSectionModel
import com.dessinemoiunalpaga.website.application.PageModel
import com.dessinemoiunalpaga.website.application.SectionColor
import com.dessinemoiunalpaga.website.application.SectionModel
import com.dessinemoiunalpaga.website.application.usecases.ShowFactsheetPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.domain.Highlight
import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
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
