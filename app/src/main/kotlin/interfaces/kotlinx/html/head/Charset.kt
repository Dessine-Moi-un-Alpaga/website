package be.alpago.website.interfaces.kotlinx.html.head

import kotlinx.html.HEAD
import kotlinx.html.meta

fun HEAD.charset() {
    meta {
        charset = "utf8"
    }
}
