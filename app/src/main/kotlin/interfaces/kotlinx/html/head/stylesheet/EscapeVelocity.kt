package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.*

fun HEAD.escapeVelocityStylesheet() {
    link {
        rel = LinkRel.stylesheet
        href = "/assets/css/escape-velocity/main.css"
    }
}
