package be.alpago.website.libs.page.template.footer.contact.info

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.template.style.EscapeVelocity
import be.alpago.website.libs.page.template.style.FontAwesome
import kotlinx.html.*

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
