package be.alpago.website.interfaces.kotlinx.html.head.script

import kotlinx.html.HEAD
import kotlinx.html.script

fun HEAD.jquery() {
    script {
        src = "/webjars/jquery/jquery.min.js"
    }
}
