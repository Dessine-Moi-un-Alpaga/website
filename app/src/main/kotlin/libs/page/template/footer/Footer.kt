package be.alpago.website.libs.page.template.footer

import be.alpago.website.libs.page.template.footer.contact.contact
import be.alpago.website.libs.page.template.footer.legal.legalNotice
import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.Entities
import kotlinx.html.SECTION
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.p
import kotlinx.html.section
import kotlinx.html.unsafe

fun DIV.footer() {
    section {
        classes = setOf(EscapeVelocity.wrapper)
        id = EscapeVelocity.footer

        footerTitle()

        div {
            classes = setOf(EscapeVelocity.container)

            footerHeader()
            contact()
            legalNotice()
        }
    }
}

private fun SECTION.footerTitle() {
    div {
        classes = setOf(EscapeVelocity.title)
        +"Nous contacter"
    }
}

private fun DIV.footerHeader() {
    header {
        classes = setOf(EscapeVelocity.style1)

        h2 {
            +"Vous souhaitez nous contacter"

            unsafe {
                +Entities.nbsp.text
            }

            +"?"
        }



        p {
            +"Nous nous ferons un plaisir de répondre à toutes vos questions"

            unsafe {
                +Entities.nbsp.text
            }

            +"!"
        }
    }
}
