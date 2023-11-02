package be.alpago.website.modules.index

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.highlight.Highlight
import be.alpago.website.modules.image.ImageMetadata

private val DESCRIPTION = "${Messages.indexPageDescription}"
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.presentation)}"

class IndexPageModelFactory(
    private val animalRepository: CrudRepository<Animal>,
    private val articleRepository: CrudRepository<Article>,
    private val guildRepository: CrudRepository<Highlight>,
    private val newsRepository: CrudRepository<Highlight>,
    private val trainingRepository: CrudRepository<ImageMetadata>,
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
