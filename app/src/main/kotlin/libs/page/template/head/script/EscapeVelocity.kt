package be.alpago.website.libs.page.template.head.script

import kotlinx.html.*

fun HEAD.escapeVelocity() {
    script {
        src = "/webjars/escape-velocity/js/util.js"
    }
    script {
        async = true
        src = "/webjars/escape-velocity/js/main.js"
    }
}
