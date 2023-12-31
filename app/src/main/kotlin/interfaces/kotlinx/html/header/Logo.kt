package be.alpago.website.interfaces.kotlinx.html.header

import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import kotlinx.html.*

const val LOGO_PATH = "images/logo.png"

private val LOGO_ALTERNATE_TEXT = "${Messages.logoAlt}"

fun SECTION.logo(properties: TemplateProperties) {
    div {
        classes = setOf("custom-logo")

        img {
            alt = LOGO_ALTERNATE_TEXT
            id = "custom-logo"
            src = "${properties.baseAssetUrl}/$LOGO_PATH"
        }
    }
}
