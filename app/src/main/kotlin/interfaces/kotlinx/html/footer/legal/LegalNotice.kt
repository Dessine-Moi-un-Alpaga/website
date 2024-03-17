package be.alpago.website.interfaces.kotlinx.html.footer.legal

import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.ul

fun DIV.legalNotice() {
    div {
        id = EscapeVelocity.copyright

        ul {
            copyright()
            design()
            graphics()
        }
    }
}
