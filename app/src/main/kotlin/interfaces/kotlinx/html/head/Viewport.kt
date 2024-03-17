package be.alpago.website.interfaces.kotlinx.html.head

import kotlinx.html.*

fun HEAD.viewport() {
    meta {
        name = "viewport"
        content = "width=device-width, initial-scale=1, user-scalable=no"
    }
}
