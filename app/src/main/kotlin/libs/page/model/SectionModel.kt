package be.alpago.website.libs.page.model

import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.FiberAnalysis
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.highlight.Highlight
import be.alpago.website.modules.image.ImageMetadata

enum class SectionColor {
    GREY,
    RED,
    WHITE,
}

sealed class SectionModel(
    val id: String,
    val isPhotoGallery: Boolean,
    val color: SectionColor,
    val sectionTitle: String
)

class AnimalSectionModel(
    id: String,
    val animal: Animal,
    val fiberAnalyses: List<FiberAnalysis>,
) : SectionModel(id, false, SectionColor.WHITE, animal.name)

class ArticleSectionModel(
    id: String,
    color: SectionColor,
    val article: Article
) : SectionModel(id, false, color, article.sectionTitle)

class PhotoGallerySectionModel(
    id: String,
    val images: List<ImageMetadata>,
    color: SectionColor,
    sectionTitle: String,
    val subtitle: String? = null,
    val title: String? = null,
) : SectionModel(id, true, color, sectionTitle)

class HighlightsSectionModel(
    id: String,
    color: SectionColor,
    title: String,
    val highlights: List<Highlight>
) : SectionModel(id, false, color, title)
