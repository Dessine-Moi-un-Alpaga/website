package be.alpago.website.libs.page.template.footer.legal

import kotlinx.html.*
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
