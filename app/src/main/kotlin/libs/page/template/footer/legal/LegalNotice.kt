package be.alpago.website.libs.page.template.footer.legal

import be.alpago.website.libs.page.template.style.EscapeVelocity
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
