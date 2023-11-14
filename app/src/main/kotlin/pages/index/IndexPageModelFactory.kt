package be.alpago.website.pages.index

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.article.Article
import be.alpago.website.domain.highlight.Highlight
import be.alpago.website.domain.image.ImageMetadata

private val DESCRIPTION = "${Messages.indexPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.presentation)}"

class IndexPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val articleRepository: Repository<Article>,
    private val guildRepository: Repository<Highlight>,
    private val newsRepository: Repository<Highlight>,
    private val trainingRepository: Repository<ImageMetadata>,
) {

    suspend fun create(): PageModel {
        val animals = animalRepository.findAll()
        val pageModel = PageModel(
            title = TITLE,
            description = DESCRIPTION,
            animals = animals,
        )

        val articles = articleRepository.findAll()

        if (articles.isNotEmpty()) {
            pageModel.createArticleSection(articles.first(), SectionColor.WHITE)
        }

        val news = newsRepository.findAll()
        pageModel.createHighlightSection("${Messages.news}", news, SectionColor.GREY)

        val trainings = trainingRepository.findAll()
        pageModel.createPhotoGallerySection(
            color = SectionColor.RED,
            images = trainings,
            sectionTitle = "${Messages.trainings}",
        )

        val guilds = guildRepository.findAll()
        pageModel.createHighlightSection("${Messages.guilds}", guilds, SectionColor.GREY)

        return pageModel
    }
}
