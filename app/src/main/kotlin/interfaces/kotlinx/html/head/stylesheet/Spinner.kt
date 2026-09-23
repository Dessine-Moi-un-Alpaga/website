package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.*

fun HEAD.spinner() {
    link {
        rel = LinkRel.stylesheet
        href = "/assets/css/spin.js/spin.css"
    }
}
