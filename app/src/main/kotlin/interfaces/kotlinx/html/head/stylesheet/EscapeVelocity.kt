package be.alpago.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.*

fun HEAD.escapeVelocityStylesheet() {
    link {
        rel = LinkRel.stylesheet
        href = "/webjars/escape-velocity/css/main.css"
    }
}
