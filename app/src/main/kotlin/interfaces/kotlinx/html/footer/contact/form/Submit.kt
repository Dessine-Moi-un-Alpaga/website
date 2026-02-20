package be.alpago.website.interfaces.kotlinx.html.footer.contact.form

import be.alpago.website.i18n.Messages
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun DIV.sendButton() {
    div {
        classes = setOf(EscapeVelocity.col12)

        ul {
            classes = setOf(EscapeVelocity.actions)

            li {
                input {
                    classes = setOf(EscapeVelocity.style1)
                    type = InputType.submit
                    value = capitalize(Messages.sendButton)
                }
            }
        }
    }
}
