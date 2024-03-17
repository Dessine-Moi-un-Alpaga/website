package be.alpago.website.interfaces.kotlinx.html.footer.legal

import kotlinx.html.Entities
import kotlinx.html.UL
import kotlinx.html.li
import kotlinx.html.unsafe
import java.time.LocalDate.now

fun UL.copyright() {
    li {
        unsafe {
            +Entities.copy.text
            +Entities.nbsp.text
        }
        +"${now().year}"
        unsafe {
            +Entities.nbsp.text
        }
        +"Alpago"
    }
}
