package be.alpago.website.libs.page.template.footer.contact.form

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.template.style.EscapeVelocity
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
