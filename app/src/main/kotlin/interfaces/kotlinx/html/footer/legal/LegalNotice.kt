package be.alpago.website.interfaces.kotlinx.html.footer.legal

import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import kotlinx.html.*

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
