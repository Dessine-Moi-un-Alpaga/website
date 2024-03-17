package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.HighlightsSectionModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

class HighlightsTemplate(
    private val model: HighlightsSectionModel,
    private val properties: TemplateProperties,
) : Template<FlowContent> {

    override fun FlowContent.apply() {
        insert(SectionTemplate(model, properties)) {
            title {
                +model.sectionTitle
            }
            content {
                div {
                    classes = setOf(
                        EscapeVelocity.row,
                        EscapeVelocity.alnCenter
                    )

                    for (highlight in model.highlights) {
                        insert(
                            HighlightTemplate(
                                id = model.id,
                                model = highlight,
                                properties,
                            )
                        ) { }
                    }
                }
            }
        }
    }
}
