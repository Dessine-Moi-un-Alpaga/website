package be.alpago.website.application

import be.alpago.website.domain.Animal
import be.alpago.website.domain.Article
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.domain.Highlight
import be.alpago.website.domain.ImageMetadata

sealed class SectionModel(
    val id: String,
    val color: SectionColor,
    val isPhotoGallery: Boolean = false,
    val sectionTitle: String,
)

class AnimalSectionModel(
    val animal: Animal,
    val fiberAnalyses: List<FiberAnalysis>,
) : SectionModel(
    id = animal.id,
    color = SectionColor.WHITE,
    sectionTitle = animal.name,
)

class ArticleSectionModel(
    val article: Article,
    color: SectionColor,
) : SectionModel(
    color = color,
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
    color = color,
    id = "photos",
    isPhotoGallery = true,
    sectionTitle = sectionTitle,
)

class HighlightsSectionModel(
    color: SectionColor,
    val highlights: List<Highlight>,
    sectionTitle: String,
) : SectionModel(
    color = color,
    id = "highlights",
    sectionTitle = sectionTitle,
)

enum class SectionColor {
    GREY,
    RED,
    WHITE,
}
