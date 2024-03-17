package be.alpago.website.interfaces.kotlinx.html.footer.contact.info

import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.FontAwesome
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.p
import kotlinx.html.section

fun DIV.phone() {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        section {
            h3 {
                classes = setOf(
                    EscapeVelocity.icon,
                    FontAwesome.phone,
                )
                +capitalize(Messages.phone)
            }
            p {
                +"+32471785171"
            }
        }
    }
}
