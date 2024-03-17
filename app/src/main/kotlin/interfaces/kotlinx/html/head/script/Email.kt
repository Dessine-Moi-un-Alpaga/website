package be.alpago.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.email() {
    script {
        async = true
        src = "/assets/js/email.js"
    }
}
