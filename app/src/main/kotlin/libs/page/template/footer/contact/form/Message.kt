package be.alpago.website.libs.page.template.footer.contact.form

import be.alpago.website.libs.page.template.style.EscapeVelocity
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
            placeholder = "Message"
            required = true
            rows = "4"
        }
    }
}
