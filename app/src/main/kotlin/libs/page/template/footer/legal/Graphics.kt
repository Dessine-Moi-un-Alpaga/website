package be.alpago.website.libs.page.template.footer.legal

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
import kotlinx.html.Entities
import kotlinx.html.UL
import kotlinx.html.li
import kotlinx.html.unsafe

fun UL.graphics() {
    li {
        +"${capitalize(Messages.graphicArt)}:"

        unsafe {
            +Entities.nbsp.text
        }

        +"Indélébile"
    }
}
