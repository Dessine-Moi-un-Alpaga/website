package be.alpago.website.interfaces.kotlinx.html.footer.contact.info

import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.FontAwesome
import kotlinx.html.*

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
