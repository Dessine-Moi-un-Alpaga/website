package be.alpago.website.libs.page.template.header

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.i18n.Messages
import kotlinx.html.SECTION
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.img

const val LOGO_PATH = "images/logo.png"

val LOGO_ALTERNATE_TEXT = "${Messages.logoAlt}"

fun SECTION.logo(environment: Environment) {
    div {
        classes = setOf("custom-logo")

        img {
            alt = LOGO_ALTERNATE_TEXT
            id = "custom-logo"
            src = "${environment.baseAssetUrl}/$LOGO_PATH"
        }
    }
}
