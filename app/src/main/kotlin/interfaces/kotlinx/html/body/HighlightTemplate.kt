package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.testId
import io.ktor.server.html.Template
import kotlinx.html.*

private fun Highlight.resolveLink(properties: TemplateProperties) = link.replace("{{baseAssetUrl}}", properties.baseAssetUrl)

class HighlightTemplate(
    private val id: String,
    private val model: Highlight,
    private val properties: TemplateProperties,
) : Template<FlowContent> {

    override fun FlowContent.apply() {
        div {
            classes = setOf(
                EscapeVelocity.col4,
                EscapeVelocity.col12Medium
            )

            testId("${this@HighlightTemplate.id}-highlight", properties)

            section {
                classes = setOf(EscapeVelocity.highlight)

                a {
                    classes = setOf(
                        EscapeVelocity.image,
                        EscapeVelocity.featured
                    )
                    testId("${this@HighlightTemplate.id}-highlight-thumbnail", properties)
                    href = model.resolveLink(properties)

                    img {
                        testId("${this@HighlightTemplate.id}-highlight-thumbnail-image", properties)
                        alt = model.thumbnailDescription
                        src = "${properties.baseAssetUrl}/${model.thumbnail}"
                    }
                }

                h3 {
                    a {
                        testId("${this@HighlightTemplate.id}-highlight-title", properties)
                        href = model.resolveLink(properties)

                        unsafe {
                            +model.title
                        }
                    }
                }

                if (model.text != null) {
                    p {
                        testId("${this@HighlightTemplate.id}-highlight-text", properties)
                        unsafe {
                            +model.text
                        }
                    }
                }

                ul {
                    classes = setOf(EscapeVelocity.actions)

                    li {
                        a {
                            classes = setOf(
                                EscapeVelocity.button,
                                EscapeVelocity.style1
                            )
                            testId("${this@HighlightTemplate.id}-highlight-button", properties)
                            href = model.resolveLink(properties)
                            +"${Messages.moreAbout}"
                        }
                    }
                }
            }
        }
    }
}
