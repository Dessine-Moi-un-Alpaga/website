package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.HighlightsSectionModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class HighlightsTemplate(
    private val properties: TemplateProperties,
    private val model: HighlightsSectionModel
) : Template<FlowContent> {

    override fun FlowContent.apply() {
        insert(SectionTemplate(model)) {
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
                        insert(HighlightTemplate(properties, highlight)) { }
                    }
                }
            }
        }
    }
}
