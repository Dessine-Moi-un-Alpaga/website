package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.PhotoGallerySectionModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.Photoswipe
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class PhotoGallerySectionTemplate(
    private val properties: TemplateProperties,
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
                                href = "${properties.baseAssetUrl}/${image.path}"

                                img {
                                    alt = image.description
                                    src = "${properties.baseAssetUrl}/${image.thumbnailPath}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
