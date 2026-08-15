package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.contact.info

import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.FontAwesome
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun DIV.email(properties: TemplateProperties) {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        section {
            h3 {
                classes = setOf(
                    EscapeVelocity.icon,
                    FontAwesome.envelope
                )
                +capitalize(Messages.email)
            }
            p {
                a {
                    href = "mailto:${properties.emailAddress}"
                    +properties.emailAddress
                }
            }
        }
    }
}
