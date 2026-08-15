package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.legal

import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun UL.design() {
    li {
        +"${capitalize(Messages.design)}:"

        unsafe {
            +Entities.nbsp.text
        }

        a {
            href = "https://html5up.net"
            +"HTML5 UP"
        }
    }
}
