package be.alpago.website.libs.page.template.head.script

import kotlinx.html.*

fun HEAD.jquery() {
    script {
        src = "/webjars/jquery/jquery.min.js"
    }
}
