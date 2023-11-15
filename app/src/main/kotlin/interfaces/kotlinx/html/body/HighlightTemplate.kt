package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import io.ktor.server.html.Template
import kotlinx.html.*

private fun Highlight.resolveLink(properties: TemplateProperties) = link.replace("{{baseAssetUrl}}", properties.baseAssetUrl)

class HighlightTemplate(
    private val properties: TemplateProperties,
    private val highlight: Highlight,
) : Template<FlowContent> {

    override fun FlowContent.apply() {
        div {
            classes = setOf(
                EscapeVelocity.col4,
                EscapeVelocity.col12Medium
            )
            section {
                classes = setOf(EscapeVelocity.highlight)

                a {
                    classes = setOf(
                        EscapeVelocity.image,
                        EscapeVelocity.featured
                    )
                    href = highlight.resolveLink(properties)

                    img {
                        alt = highlight.thumbnailDescription
                        src = "${properties.baseAssetUrl}/${highlight.thumbnail}"
                    }
                }

                h3 {
                    a {
                        href = highlight.resolveLink(properties)

                        unsafe {
                            +highlight.title
                        }
                    }
                }

                if (highlight.text != null) {
                    p {
                        unsafe {
                            +highlight.text
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
                            href = highlight.resolveLink(properties)
                            +"${Messages.moreAbout}"
                        }
                    }
                }
            }
        }
    }
}
