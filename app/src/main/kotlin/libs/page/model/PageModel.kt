package be.alpago.website.libs.page.model

import be.alpago.website.libs.page.template.style.EscapeVelocity
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.FiberAnalysis
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.highlight.Highlight
import be.alpago.website.modules.image.ImageMetadata

class PageModel(
    val title: String,
    val description: String,
    animals: List<Animal>,
) {
    val navigationModel = NavigationModel(animals)

    private val _sections = mutableListOf<SectionModel>()

    val sections
        get() = _sections.toList()

    fun hasPhotoGallery() = sections.any { it.isPhotoGallery }

    fun createAnimalSection(animal: Animal, fiberAnalyses: List<FiberAnalysis>) {
        _sections.add(
            AnimalSectionModel(
                id = animal.id,
                animal = animal,
                fiberAnalyses = fiberAnalyses,
            )
        )
    }

    fun createArticleSection(article: Article, color: SectionColor) {
        _sections.add(
            ArticleSectionModel(
                id = EscapeVelocity.main,
                color = color,
                article = article,
            )
        )
    }

    fun createHighlightSection(title: String, highlights: List<Highlight>, color: SectionColor) {
        _sections.add(
            HighlightsSectionModel(
                id = EscapeVelocity.highlights,
                color = color,
                title = title,
                highlights = highlights,
            )
        )
    }

    fun createPhotoGallerySection(
        color: SectionColor,
        images: List<ImageMetadata>,
        sectionTitle: String,
        subtitle: String? = null,
        title: String? = null,
    ) {
        _sections.add(
            PhotoGallerySectionModel(
                color = color,
                id = "photos", // TODO
                images = images,
                sectionTitle = sectionTitle,
                subtitle = subtitle,
                title = title,
            )
        )
    }
}
