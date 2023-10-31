package be.alpago.website.libs.page.template.footer.contact.form

import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.input

fun DIV.nameInput() {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        input {
            id = "contact-name"
            maxLength = "100"
            name = "name"
            placeholder = "Nom"
            required = true
            type = InputType.text
        }
    }
}
