package be.alpago.website.libs.page.template.body

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.page.template.style.EscapeVelocity
import be.alpago.website.domain.highlight.Highlight
import io.ktor.server.html.Template
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.img
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.section
import kotlinx.html.ul
import kotlinx.html.unsafe

private fun Highlight.resolveLink(environment: Environment) = link.replace("{{baseAssetUrl}}", environment.baseAssetUrl)

class HighlightTemplate(
    private val environment: Environment,
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
                    href = highlight.resolveLink(environment)

                    img {
                        alt = highlight.thumbnailDescription
                        src = "${environment.baseAssetUrl}/${highlight.thumbnail}"
                    }
                }

                h3 {
                    a {
                        href = highlight.resolveLink(environment)

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
                            href = highlight.resolveLink(environment)
                            +"${Messages.moreAbout}"
                        }
                    }
                }
            }
        }
    }
}
