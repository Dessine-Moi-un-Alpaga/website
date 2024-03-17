package be.alpago.website.interfaces.kotlinx.html.head.script

import kotlinx.html.HEAD
import kotlinx.html.script

fun HEAD.dropotron() {
    script {
        src = "/webjars/jquery.dropotron/jquery.dropotron.min.js"
    }
}
