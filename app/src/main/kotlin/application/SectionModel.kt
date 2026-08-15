package com.dessinemoiunalpaga.website.application

import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.domain.FiberAnalysis
import com.dessinemoiunalpaga.website.domain.Highlight
import com.dessinemoiunalpaga.website.domain.ImageMetadata

sealed class SectionModel(
    val color: SectionColor,
    val id: String,
    val sectionTitle: String,
    val type: String,
    val isPhotoGallery: Boolean = false,
)

class AnimalSectionModel(
    val animal: Animal,
    val fiberAnalyses: List<FiberAnalysis>,
    id: String,
) : SectionModel(
    color = SectionColor.WHITE,
    id,
    sectionTitle = animal.name,
    type = "animal",
)

class ArticleSectionModel(
    val article: Article,
    color: SectionColor,
    id: String,
) : SectionModel(
    color,
    id,
    sectionTitle = article.sectionTitle,
    type = "article",
)

class PhotoGallerySectionModel(
    color: SectionColor,
    id: String,
    val images: List<ImageMetadata>,
    sectionTitle: String,
    val subtitle: String? = null,
    val title: String? = null,
) : SectionModel(
    color,
    id,
    sectionTitle,
    type = "photos",
    isPhotoGallery = true,
)

class HighlightsSectionModel(
    color: SectionColor,
    val highlights: List<Highlight>,
    id: String,
    sectionTitle: String,
) : SectionModel(
    color,
    id,
    sectionTitle,
    type = "highlights",
)

enum class SectionColor {
    GREY,
    RED,
    WHITE,
}
