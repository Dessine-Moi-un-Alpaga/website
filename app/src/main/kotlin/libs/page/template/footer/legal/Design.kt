package be.alpago.website.libs.page.template.footer.legal

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
import kotlinx.html.Entities
import kotlinx.html.UL
import kotlinx.html.a
import kotlinx.html.li
import kotlinx.html.unsafe

fun UL.design() {
    li {
        +"${capitalize(Messages.design)}:"

        unsafe {
            +Entities.nbsp.text
        }

        a {
            href = "https://html5up.net"
            +"HTML5 UP"
        }
    }
}
