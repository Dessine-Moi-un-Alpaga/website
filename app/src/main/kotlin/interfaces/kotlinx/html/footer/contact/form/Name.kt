package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.contact.form

import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun DIV.nameInput() {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        input {
            id = "contact-name"
            maxLength = "100"
            name = "name"
            placeholder = capitalize(Messages.name)
            required = true
            type = InputType.text
        }
    }
}
