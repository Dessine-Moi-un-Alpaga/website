package be.alpago.website.libs.page.template.body

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.ArticleSectionModel
import be.alpago.website.libs.page.template.style.EscapeVelocity
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class ArticleSectionTemplate(
    private val environment: Environment,
    private val model: ArticleSectionModel,
) : Template<FlowContent> {

    override fun FlowContent.apply() {
        insert(SectionTemplate(model)) {
            pageTitle {
                +model.sectionTitle
            }
            content {
                article {
                    classes = setOf(
                        EscapeVelocity.box,
                        EscapeVelocity.post
                    )

                    if (model.article.title != null) {
                        header {
                            classes = setOf(EscapeVelocity.style1)

                            h2 {
                                unsafe {
                                    +model.article.title
                                }
                            }

                            if (model.article.subtitle != null) {
                                p {
                                    unsafe {
                                        +model.article.subtitle
                                    }
                                }
                            }
                        }
                    }

                    if (model.article.banner != null) {
                        a {
                            classes = setOf(
                                EscapeVelocity.image,
                                EscapeVelocity.featured
                            )

                            img {
                                if (model.article.bannerDescription != null) {
                                    alt = model.article.bannerDescription
                                }

                                src = "${environment.baseAssetUrl}/${model.article.banner}"
                            }
                        }
                    }

                    unsafe {
                        +model.article.text
                    }
                }
            }
        }
    }
}
