package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.HighlightsSectionModel
import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.testId
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

private fun Highlight.resolveLink(properties: TemplateProperties) = link.replace("{{baseAssetUrl}}", properties.baseAssetUrl)

class HighlightsTemplate(
    private val model: HighlightsSectionModel,
    private val properties: TemplateProperties,
) : Template<FlowContent> {

    private fun SECTION.thumbnail(highlight: Highlight) {
        a {
            classes = setOf(
                EscapeVelocity.image,
                EscapeVelocity.featured
            )
            testId("${model.id}-highlight-thumbnail", properties)
            href = highlight.resolveLink(properties)

            img {
                testId("${model.id}-highlight-thumbnail-image", properties)
                alt = highlight.thumbnailDescription
                src = "${properties.baseAssetUrl}/${highlight.thumbnail}"
            }
        }
    }

    private fun SECTION.title(highlight: Highlight) {
        h3 {
            a {
                testId("${model.id}-highlight-title", properties)
                href = highlight.resolveLink(properties)

                unsafe {
                    +highlight.title
                }
            }
        }
    }

    private fun SECTION.text(highlight: Highlight) {
        if (highlight.text != null) {
            p {
                testId("${model.id}-highlight-text", properties)
                unsafe {
                    +highlight.text
                }
            }
        }
    }

    private fun SECTION.button(highlight: Highlight) {
        ul {
            classes = setOf(EscapeVelocity.actions)

            li {
                a {
                    classes = setOf(
                        EscapeVelocity.button,
                        EscapeVelocity.style1
                    )
                    testId("${model.id}-highlight-button", properties)
                    href = highlight.resolveLink(properties)
                    +"${Messages.moreAbout}"
                }
            }
        }
    }

    private fun DIV.highlight(highlight: Highlight) {
        div {
            classes = setOf(
                EscapeVelocity.col4,
                EscapeVelocity.col12Medium
            )

            testId("${model.id}-highlight", properties)

            section {
                classes = setOf(EscapeVelocity.highlight)

                thumbnail(highlight)
                title(highlight)
                text(highlight)
                button(highlight)
            }
        }
    }

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
                        highlight(highlight)
                    }
                }
            }
        }
    }
}
