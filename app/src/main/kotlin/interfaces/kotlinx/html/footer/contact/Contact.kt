package be.alpago.website.interfaces.kotlinx.html.footer.contact

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.footer.contact.form.contactForm
import be.alpago.website.interfaces.kotlinx.html.footer.contact.info.contactInformation
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div

fun DIV.contact(properties: TemplateProperties) {
    div {
        classes = setOf(EscapeVelocity.row)

        contactForm()
        contactInformation(properties)
    }
}
