package be.alpago.website.application

import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata

sealed class SectionModel(
    val color: SectionColor,
    val id: String,
    val sectionTitle: String,
    val isPhotoGallery: Boolean = false,
)

class AnimalSectionModel(
    val animal: Animal,
    val fiberAnalyses: List<FiberAnalysis>,
) : SectionModel(
    color = SectionColor.WHITE,
    id = animal.id,
    sectionTitle = animal.name,
)

class ArticleSectionModel(
    val article: Article,
    color: SectionColor,
) : SectionModel(
    color,
    id = "article",
    sectionTitle = article.sectionTitle,
)

class PhotoGallerySectionModel(
    color: SectionColor,
    val images: List<ImageMetadata>,
    sectionTitle: String,
    val subtitle: String? = null,
    val title: String? = null,
) : SectionModel(
    color,
    id = "photos",
    sectionTitle,
    isPhotoGallery = true,
)

class HighlightsSectionModel(
    color: SectionColor,
    val highlights: List<Highlight>,
    sectionTitle: String,
) : SectionModel(
    color,
    id = "highlights",
    sectionTitle,
)

enum class SectionColor {
    GREY,
    RED,
    WHITE,
}
