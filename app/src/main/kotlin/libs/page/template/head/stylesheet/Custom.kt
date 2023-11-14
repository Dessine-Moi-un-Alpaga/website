package be.alpago.website.libs.page.template.head.stylesheet

import kotlinx.html.*

fun HEAD.customStylesheets() {
    link {
        rel = LinkRel.stylesheet
        href = "/assets/css/main.css"
    }
}
