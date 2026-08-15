package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.*

fun HEAD.customStylesheets() {
    link {
        rel = LinkRel.stylesheet
        href = "/assets/css/main.css"
    }
}
