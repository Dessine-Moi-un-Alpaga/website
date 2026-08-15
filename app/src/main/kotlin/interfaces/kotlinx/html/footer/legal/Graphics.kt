package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.footer.legal

import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

fun UL.graphics() {
    li {
        +"${capitalize(Messages.graphicArt)}:"

        unsafe {
            +Entities.nbsp.text
        }

        +"Indélébile"
    }
}
