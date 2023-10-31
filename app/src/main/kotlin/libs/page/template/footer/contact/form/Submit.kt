package be.alpago.website.libs.page.template.footer.contact.form

import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.input
import kotlinx.html.li
import kotlinx.html.ul

fun DIV.sendButton() {
    div {
        classes = setOf(EscapeVelocity.col12)

        ul {
            classes = setOf(EscapeVelocity.actions)

            li {
                input {
                    classes = setOf(EscapeVelocity.style1)
                    type = InputType.submit
                    value = "Envoyer"
                }
            }
        }
    }
}
