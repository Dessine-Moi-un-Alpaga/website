package be.alpago.website.interfaces.kotlinx.html.footer

import be.alpago.website.i18n.Messages
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.footer.contact.contact
import be.alpago.website.interfaces.kotlinx.html.footer.legal.legalNotice
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.libs.kotlin.i18n.capitalize
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
