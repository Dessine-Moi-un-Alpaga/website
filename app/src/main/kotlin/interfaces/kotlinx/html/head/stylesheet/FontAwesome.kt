package be.alpago.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.*

fun HEAD.fontAwesomeStylesheets() {
    link {
        rel = LinkRel.stylesheet
        href = "/webjars/font-awesome/css/fontawesome.min.css"
    }
    link {
        rel = LinkRel.stylesheet
        href = "/webjars/font-awesome/css/solid.min.css"
    }
    link {
        rel = LinkRel.stylesheet
        href = "/webjars/font-awesome/css/brands.min.css"
    }
}
