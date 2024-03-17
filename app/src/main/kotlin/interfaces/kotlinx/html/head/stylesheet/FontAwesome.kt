package be.alpago.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.HEAD
import kotlinx.html.LinkRel
import kotlinx.html.link

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
