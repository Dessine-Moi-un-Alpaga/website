package be.alpago.website.libs.page.template.footer.contact.info

import be.alpago.website.libs.page.template.style.EscapeVelocity
import be.alpago.website.libs.page.template.style.FontAwesome
import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.p
import kotlinx.html.section

fun DIV.social() {
    div {
        classes = setOf(
            EscapeVelocity.col6,
            EscapeVelocity.col12Small,
        )

        section {
            h3 {
                classes = setOf(
                    EscapeVelocity.icon,
                    FontAwesome.social,
                )
                +"Facebook"
            }
            p {
                a {
                    href = "https://www.facebook.com/DessineMoiUnAlpaga/"
                    +"Dessine-Moi un Alpaga"
                }
            }
        }
    }
}