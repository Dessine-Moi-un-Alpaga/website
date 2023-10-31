package be.alpago.website.libs.page.template.head

import kotlinx.html.HEAD
import kotlinx.html.meta

fun HEAD.charset() {
    meta {
        charset = "utf8"
    }
}
