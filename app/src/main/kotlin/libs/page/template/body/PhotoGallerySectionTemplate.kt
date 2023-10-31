package be.alpago.website.libs.page.template.body

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.PhotoGallerySectionModel
import be.alpago.website.libs.page.template.style.EscapeVelocity
import be.alpago.website.libs.page.template.style.Photoswipe
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.figure
import kotlinx.html.h2
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.html.unsafe

class PhotoGallerySectionTemplate(
    private val environment: Environment,
    private val model: PhotoGallerySectionModel
) : Template<FlowContent> {

    override fun FlowContent.apply() {
        insert(SectionTemplate(model)) {
            title {
                +model.sectionTitle
            }
            content {
                if (model.title != null) {
                    header {
                        classes = setOf(EscapeVelocity.style1)

                        h2 {
                            unsafe {
                                +model.title
                            }
                        }

                        if (model.subtitle != null) {
                            p {
                                unsafe {
                                    +model.subtitle
                                }
                            }
                        }
                    }
                }
                div {
                    attributes["itemscope"] = "itemscope"
                    attributes["itemtype"] = "http://schema.org/ImageGallery"

                    classes = setOf(
                        Photoswipe.gallery,
                        EscapeVelocity.row,
                        EscapeVelocity.alnCenter,
                        EscapeVelocity.alnMiddle
                    )

                    id = "gallery"

                    for (image in model.images) {
                        figure {
                            attributes["itemprop"] = "associatedMedia"
                            attributes["itemscope"] = "itemscope"
                            attributes["itemtype"] = "http://schema.org/ImageObject"

                            classes = setOf("gallery-image")

                            a {
                                attributes["itemprop"] = "contentUrl"
                                attributes[Photoswipe.height] = "${image.height}"
                                attributes[Photoswipe.width] = "${image.width}"
                                href = "${environment.baseAssetUrl}/${image.path}"

                                img {
                                    alt = image.description
                                    src = "${environment.baseAssetUrl}/${image.thumbnailPath}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
