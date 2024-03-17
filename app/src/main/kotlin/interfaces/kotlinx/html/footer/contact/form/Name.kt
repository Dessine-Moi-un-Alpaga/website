package be.alpago.website.interfaces.kotlinx.html.footer.contact.form

import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.libs.kotlin.i18n.capitalize
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
            placeholder = capitalize(Messages.name)
            required = true
            type = InputType.text
        }
    }
}
