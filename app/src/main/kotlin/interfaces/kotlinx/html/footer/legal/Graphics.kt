package be.alpago.website.interfaces.kotlinx.html.footer.legal

import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun UL.graphics() {
    li {
        +"${capitalize(Messages.graphicArt)}:"

        unsafe {
            +Entities.nbsp.text
        }

        +"Indélébile"
    }
}
