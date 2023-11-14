package be.alpago.website.libs.page.template.footer.legal

import be.alpago.website.libs.page.template.style.EscapeVelocity
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
