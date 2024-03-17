package be.alpago.website.interfaces.kotlinx.html.head

import kotlinx.html.HEAD
import kotlinx.html.meta

fun HEAD.viewport() {
    meta {
        name = "viewport"
        content = "width=device-width, initial-scale=1, user-scalable=no"
    }
}
