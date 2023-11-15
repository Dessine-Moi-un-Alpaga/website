package be.alpago.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.jquery() {
    script {
        src = "/webjars/jquery/jquery.min.js"
    }
}
