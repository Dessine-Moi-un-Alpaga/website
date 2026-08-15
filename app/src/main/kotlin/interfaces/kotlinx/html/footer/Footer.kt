package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer

import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.contact.contact
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.legal.legalNotice
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun DIV.footer(properties: TemplateProperties) {
    section {
        classes = setOf(EscapeVelocity.wrapper)
        id = EscapeVelocity.footer

        footerTitle()

        div {
            classes = setOf(EscapeVelocity.container)

            footerHeader()
            contact(properties)
            legalNotice()
        }
    }
}

private fun SECTION.footerTitle() {
    div {
        classes = setOf(EscapeVelocity.title)
        +"${Messages.footerHeaderTitle}"
    }
}

private fun DIV.footerHeader() {
    header {
        classes = setOf(EscapeVelocity.style1)

        h2 {
            +capitalize(Messages.footerHeaderLine1)
        }

        p {
            +capitalize(Messages.footerHeaderLine2)
        }
    }
}
