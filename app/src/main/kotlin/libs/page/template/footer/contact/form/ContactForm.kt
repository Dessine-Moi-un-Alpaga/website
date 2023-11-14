package be.alpago.website.libs.page.template.footer.contact.form

import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.*

open class CONTACTFORM(
    initialAttributes : Map<String, String>,
    override val consumer : TagConsumer<*>
) : HTMLTag(
    "form",
    consumer,
    initialAttributes,
    null,
    false,
    false
), HtmlBlockTag

inline fun FlowContent.contactForm(crossinline block : CONTACTFORM.() -> Unit = {}) : Unit = CONTACTFORM(
    attributesMapOf(
        "id", "contact-form",
        "onsubmit", "sendEmail(event)",
    ), consumer
).visit(block)

fun DIV.contactForm() {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Medium,
        )

        section {
            contactForm {
                div {
                    classes = setOf(
                        EscapeVelocity.row,
                        EscapeVelocity.gtr50,
                    )

                    nameInput()
                    emailInput()
                    messageTextArea()
                    sendButton()
                }
            }
        }
    }
}
