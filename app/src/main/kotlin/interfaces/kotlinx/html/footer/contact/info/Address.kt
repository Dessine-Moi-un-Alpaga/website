package be.alpago.website.interfaces.kotlinx.html.footer.contact.info

import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.FontAwesome
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.DIV
import kotlinx.html.br
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.p
import kotlinx.html.section

fun DIV.address() {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        section {
            h3 {
                classes = setOf(
                    EscapeVelocity.icon,
                    FontAwesome.home
                )
                +capitalize(Messages.address)
            }
            p {
                +"Rue de Saint-Lô 50"; br
                +"5060 Falisolle"; br
                +"Belgique"
            }
        }
    }
}
