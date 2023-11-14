package be.alpago.website.libs.page.template.head.stylesheet

import kotlinx.html.*

fun HEAD.toastrStylesheet() {
    link {
        rel = LinkRel.stylesheet
        href = "/webjars/toastr/build/toastr.min.css"
    }
}
