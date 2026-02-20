package be.alpago.website.interfaces.kotlinx.html.footer.legal

import be.alpago.website.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

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
