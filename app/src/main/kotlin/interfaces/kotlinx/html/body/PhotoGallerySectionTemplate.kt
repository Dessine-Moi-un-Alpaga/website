package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.PhotoGallerySectionModel
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.Photoswipe
import be.alpago.website.interfaces.kotlinx.html.style.testId
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class PhotoGallerySectionTemplate(
    private val model: PhotoGallerySectionModel,
    private val properties: TemplateProperties,
) : Template<FlowContent> {

    private fun FlowContent.header() {
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
    }

    private fun DIV.figure(image: ImageMetadata) {
        figure {
            testId("${this@PhotoGallerySectionTemplate.model.id}-photo", properties)
            attributes["itemprop"] = "associatedMedia"
            attributes["itemscope"] = "itemscope"
            attributes["itemtype"] = "http://schema.org/ImageObject"

            classes = setOf("gallery-image")

            a {
                testId("${this@PhotoGallerySectionTemplate.model.id}-photo-image", properties)
                attributes["itemprop"] = "contentUrl"
                attributes[Photoswipe.height] = "${image.height}"
                attributes[Photoswipe.width] = "${image.width}"
                href = "${properties.baseAssetUrl}/${image.path}"

                img {
                    testId("${this@PhotoGallerySectionTemplate.model.id}-photo-thumbnail", properties)
                    alt = image.description
                    src = "${properties.baseAssetUrl}/${image.thumbnailPath}"
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
                header()

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
                        figure(image)
                    }
                }
            }
        }
    }
}
