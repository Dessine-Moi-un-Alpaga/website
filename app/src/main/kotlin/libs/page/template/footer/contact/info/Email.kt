package be.alpago.website.libs.page.template.footer.contact.info

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.template.style.EscapeVelocity
import be.alpago.website.libs.page.template.style.FontAwesome
import kotlinx.html.*

fun DIV.email(environment: Environment) {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        section {
            h3 {
                classes = setOf(
                    EscapeVelocity.icon,
                    FontAwesome.envelope
                )
                +capitalize(Messages.email)
            }
            p {
                a {
                    href = "mailto:${environment.emailAddress}"
                    +environment.emailAddress
                }
            }
        }
    }
}
