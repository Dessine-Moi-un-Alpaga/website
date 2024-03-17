package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.ArticleSectionModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.testId
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.ARTICLE
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.article
import kotlinx.html.classes
import kotlinx.html.h2
import kotlinx.html.header
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.html.unsafe

class ArticleSectionTemplate(
    private val model: ArticleSectionModel,
    private val properties: TemplateProperties,
) : Template<FlowContent> {

    private fun ARTICLE.title() {
        if (model.article.title != null) {
            header {
                classes = setOf(EscapeVelocity.style1)

                h2 {
                    testId("${this@ArticleSectionTemplate.model.id}-title", properties)
                    unsafe {
                        +model.article.title
                    }
                }

                if (model.article.subtitle != null) {
                    p {
                        testId("${this@ArticleSectionTemplate.model.id}-subtitle", properties)
                        unsafe {
                            +model.article.subtitle
                        }
                    }
                }
            }
        }
    }

    private fun ARTICLE.banner() {
        if (model.article.banner != null) {
            a {
                classes = setOf(
                    EscapeVelocity.image,
                    EscapeVelocity.featured
                )

                img {
                    testId("${this@ArticleSectionTemplate.model.id}-banner", properties)

                    if (model.article.bannerDescription != null) {
                        alt = model.article.bannerDescription
                    }

                    src = "${properties.baseAssetUrl}/${model.article.banner}"
                }
            }
        }
    }

    override fun FlowContent.apply() {
        insert(SectionTemplate(model, properties)) {
            title {
                +model.sectionTitle
            }
            content {
                article {
                    classes = setOf(
                        EscapeVelocity.box,
                        EscapeVelocity.post
                    )

                    title()
                    banner()

                    unsafe {
                        +model.article.text
                    }
                }
            }
        }
    }
}
