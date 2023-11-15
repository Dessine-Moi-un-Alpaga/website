package be.alpago.website.interfaces.kotlinx.html.head

import kotlinx.html.*

fun HEAD.charset() {
    meta {
        charset = "utf8"
    }
}
