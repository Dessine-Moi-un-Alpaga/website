package be.alpago.website.libs.page.template.head.script

import kotlinx.html.*

fun HEAD.email() {
    script {
        async = true
        src = "/assets/js/email.js"
    }
}
