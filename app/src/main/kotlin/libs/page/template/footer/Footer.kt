package be.alpago.website.libs.page.template.footer

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.template.footer.contact.contact
import be.alpago.website.libs.page.template.footer.legal.legalNotice
import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.*

fun DIV.footer(environment: Environment) {
    section {
        classes = setOf(EscapeVelocity.wrapper)
        id = EscapeVelocity.footer

        footerTitle()

        div {
            classes = setOf(EscapeVelocity.container)

            footerHeader()
            contact(environment)
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

            unsafe {
                +Entities.nbsp.text
            }

            +"?"
        }



        p {
            +capitalize(Messages.footerHeaderLine2)

            unsafe {
                +Entities.nbsp.text
            }

            +"!"
        }
    }
}
