package be.alpago.website.interfaces.kotlinx.html.head.script

import kotlinx.html.HEAD
import kotlinx.html.script

fun HEAD.escapeVelocity() {
    script {
        src = "/webjars/escape-velocity/js/util.js"
    }
    script {
        async = true
        src = "/webjars/escape-velocity/js/main.js"
    }
}
