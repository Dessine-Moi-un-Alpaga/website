package be.alpago.website.libs.page.template.footer.contact.form

import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.input

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
            placeholder = "Email"
            required = true
            type = InputType.text
        }
    }
}
