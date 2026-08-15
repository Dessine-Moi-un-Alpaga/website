package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.legal

import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
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
