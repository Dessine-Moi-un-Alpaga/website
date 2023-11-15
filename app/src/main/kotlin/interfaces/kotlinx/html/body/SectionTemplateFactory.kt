package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.AnimalSectionModel
import be.alpago.website.application.ArticleSectionModel
import be.alpago.website.application.HighlightsSectionModel
import be.alpago.website.application.PhotoGallerySectionModel
import be.alpago.website.application.SectionModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties

object SectionTemplateFactory {

    fun createSectionTemplate(properties: TemplateProperties, sectionModel: SectionModel) = when (sectionModel) {
        is AnimalSectionModel       -> AnimalSectionTemplate(properties, sectionModel)
        is ArticleSectionModel      -> ArticleSectionTemplate(properties, sectionModel)
        is HighlightsSectionModel   -> HighlightsTemplate(properties, sectionModel)
        is PhotoGallerySectionModel -> PhotoGallerySectionTemplate(properties, sectionModel)
    }
}
