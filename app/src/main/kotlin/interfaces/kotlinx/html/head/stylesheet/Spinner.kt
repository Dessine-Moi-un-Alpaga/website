package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.HEAD
import kotlinx.html.LinkRel
import kotlinx.html.link

fun HEAD.spinner() {
    link {
        rel = LinkRel.stylesheet
        href = "/assets/css/spin.js/spin.css"
    }
}
