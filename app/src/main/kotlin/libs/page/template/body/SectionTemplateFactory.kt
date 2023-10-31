package be.alpago.website.libs.page.template.body

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.AnimalSectionModel
import be.alpago.website.libs.page.model.ArticleSectionModel
import be.alpago.website.libs.page.model.HighlightsSectionModel
import be.alpago.website.libs.page.model.PhotoGallerySectionModel
import be.alpago.website.libs.page.model.SectionModel

object SectionTemplateFactory {

    fun createSectionTemplate(environment: Environment, sectionModel: SectionModel) = when (sectionModel) {
        is AnimalSectionModel -> AnimalSectionTemplate(environment, sectionModel)
        is ArticleSectionModel -> ArticleSectionTemplate(environment, sectionModel)
        is HighlightsSectionModel -> HighlightsTemplate(environment, sectionModel)
        is PhotoGallerySectionModel -> PhotoGallerySectionTemplate(environment, sectionModel)
    }
}
