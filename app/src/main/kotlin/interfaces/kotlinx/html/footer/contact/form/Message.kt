package be.alpago.website.interfaces.kotlinx.html.footer.contact.form

import be.alpago.website.i18n.Messages
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

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
