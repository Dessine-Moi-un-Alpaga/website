package be.alpago.website.libs.page.template.footer.contact.form

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.template.style.EscapeVelocity
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
