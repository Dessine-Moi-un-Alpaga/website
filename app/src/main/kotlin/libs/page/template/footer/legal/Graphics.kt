package be.alpago.website.libs.page.template.footer.legal

import be.alpago.website.libs.i18n.Messages
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
