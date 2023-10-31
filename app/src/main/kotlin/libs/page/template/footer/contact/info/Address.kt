package be.alpago.website.libs.page.template.footer.contact.info

import be.alpago.website.libs.page.template.style.EscapeVelocity
import be.alpago.website.libs.page.template.style.FontAwesome
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
                +"Adresse"
            }
            p {
                +"Rue de Saint-Lô 50"; br
                +"5060 Falisolle"; br
                +"Belgique"
            }
        }
    }
}
