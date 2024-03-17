package be.alpago.website.interfaces.kotlinx.html.footer.contact.info

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.section

fun DIV.contactInformation(properties: TemplateProperties) {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Medium,
        )

        section {
            classes = setOf(
                EscapeVelocity.featureList,
                EscapeVelocity.small,
            )

            div {
                classes = setOf(EscapeVelocity.row)

                address()
                phone()
                social()
                email(properties)
            }
        }
    }
}
