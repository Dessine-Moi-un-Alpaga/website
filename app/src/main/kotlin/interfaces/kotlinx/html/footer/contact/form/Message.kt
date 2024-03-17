package be.alpago.website.interfaces.kotlinx.html.footer.contact.form

import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.textArea

fun DIV.messageTextArea() {
    div {
        classes = setOf(EscapeVelocity.col12)

        textArea {
            id = "contact-message"
            maxLength = "5000"
            name = "message"
            placeholder = capitalize(Messages.message)
            required = true
            rows = "4"
        }
    }
}
