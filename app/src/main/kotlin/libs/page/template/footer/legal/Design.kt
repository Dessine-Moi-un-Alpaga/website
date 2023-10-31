package be.alpago.website.libs.page.template.footer.legal

import kotlinx.html.Entities
import kotlinx.html.UL
import kotlinx.html.a
import kotlinx.html.li
import kotlinx.html.unsafe

fun UL.design() {
    li {
        +"Design:"

        unsafe {
            +Entities.nbsp.text
        }

        a {
            href = "https://html5up.net"
            +"HTML5 UP"
        }
    }
}
