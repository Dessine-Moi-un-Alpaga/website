package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.body

import com.dessinemoiunalpaga.website.application.AnimalSectionModel
import com.dessinemoiunalpaga.website.application.ArticleSectionModel
import com.dessinemoiunalpaga.website.application.HighlightsSectionModel
import com.dessinemoiunalpaga.website.application.PhotoGallerySectionModel
import com.dessinemoiunalpaga.website.application.SectionModel
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties

object SectionTemplateFactory {

    fun createSectionTemplate(
        properties: TemplateProperties,
        sectionModel: SectionModel
    ) = when (sectionModel) {
        is AnimalSectionModel       -> AnimalSectionTemplate(sectionModel, properties)
        is ArticleSectionModel      -> ArticleSectionTemplate(sectionModel, properties)
        is HighlightsSectionModel   -> HighlightsTemplate(sectionModel, properties)
        is PhotoGallerySectionModel -> PhotoGallerySectionTemplate(sectionModel, properties)
    }
}
