package be.alpago.website.libs.page.template.head.stylesheet

import kotlinx.html.*

fun HEAD.escapeVelocityStylesheet() {
    link {
        rel = LinkRel.stylesheet
        href = "/webjars/escape-velocity/css/main.css"
    }
}
