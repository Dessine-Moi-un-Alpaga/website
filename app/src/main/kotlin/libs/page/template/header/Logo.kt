package be.alpago.website.libs.page.template.header

import be.alpago.website.libs.environment.Environment
import kotlinx.html.SECTION
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.img

const val LOGO_ALTERNATE_TEXT = "logo de l'élevage d'alpagas 'Dessine-Moi un Alpaga'"

const val LOGO_PATH = "images/logo.png"

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
