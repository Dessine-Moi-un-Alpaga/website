package be.alpago.website.modules.index

import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.highlight.Highlight
import be.alpago.website.modules.image.ImageMetadata

private const val DESCRIPTION = "Un élevage d'alpagas huacaya de qualité et à taille humaine depuis 2017. Vente d'animaux et de vêtements en alpaga 100% confectionnés en Belgique"
private const val TITLE = "Dessine-Moi un Alpaga :: Présentation"

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
        pageModel.createHighlightSection("Actualités", news, SectionColor.GREY)

        val trainings = trainingRepository.findAll()
        pageModel.createPhotoGallerySection(
            color = SectionColor.RED,
            images = trainings,
            sectionTitle = "Formations",
        )

        val guilds = guildRepository.findAll()
        pageModel.createHighlightSection("Affiliations", guilds, SectionColor.GREY)

        return pageModel
    }
}
