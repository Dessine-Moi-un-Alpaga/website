package be.alpago.website.libs.page.template.head.script

import kotlinx.html.*

fun HEAD.dropotron() {
    script {
        src = "/webjars/jquery.dropotron/jquery.dropotron.min.js"
    }
}
