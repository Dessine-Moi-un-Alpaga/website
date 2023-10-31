package be.alpago.website.libs.page.template.body

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.HighlightsSectionModel
import be.alpago.website.libs.page.template.style.EscapeVelocity
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

class HighlightsTemplate(
    private val environment: Environment,
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
                        insert(HighlightTemplate(environment, highlight)) { }
                    }
                }
            }
        }
    }
}
