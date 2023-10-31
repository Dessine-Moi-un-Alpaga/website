package be.alpago.website.libs.page.template.footer.legal

import kotlinx.html.Entities
import kotlinx.html.UL
import kotlinx.html.li
import kotlinx.html.unsafe

fun UL.graphics() {
    li {
        +"Graphisme:"

        unsafe {
            +Entities.nbsp.text
        }

        +"Indélébile"
    }
}
