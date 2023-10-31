package be.alpago.website.libs.page.template.footer.contact

import be.alpago.website.libs.page.template.footer.contact.form.contactForm
import be.alpago.website.libs.page.template.footer.contact.info.contactInformation
import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div

fun DIV.contact() {
    div {
        classes = setOf(EscapeVelocity.row)

        contactForm()
        contactInformation()
    }
}
