package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.contact

import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.contact.form.contactForm
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.contact.info.contactInformation
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
import kotlinx.html.*

fun DIV.contact(properties: TemplateProperties) {
    div {
        classes = setOf(EscapeVelocity.row)

        contactForm()
        contactInformation(properties)
    }
}
