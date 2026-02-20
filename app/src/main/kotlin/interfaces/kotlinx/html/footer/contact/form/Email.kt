package be.alpago.website.interfaces.kotlinx.html.footer.contact.form

import be.alpago.website.i18n.Messages
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun DIV.emailInput() {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        input {
            id = "contact-email"
            maxLength = "320"
            name = "email"
            placeholder = capitalize(Messages.email)
            required = true
            type = InputType.text
        }
    }
}
