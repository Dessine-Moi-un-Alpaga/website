package be.alpago.website.libs.page.template.footer.contact.info

import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.section

fun DIV.contactInformation() {
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
                email()
            }
        }
    }
}
