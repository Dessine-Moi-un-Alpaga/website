package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.domain.Highlight
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties

private fun Highlight.resolveLink(properties: TemplateProperties) = link.replace("{{baseAssetUrl}}", properties.baseAssetUrl)

