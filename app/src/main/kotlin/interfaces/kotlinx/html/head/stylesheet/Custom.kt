package be.alpago.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.HEAD
import kotlinx.html.LinkRel
import kotlinx.html.link

fun HEAD.customStylesheets() {
    link {
        rel = LinkRel.stylesheet
        href = "/assets/css/main.css"
    }
}
