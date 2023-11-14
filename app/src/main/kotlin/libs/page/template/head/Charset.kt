package be.alpago.website.libs.page.template.head

import kotlinx.html.*

fun HEAD.charset() {
    meta {
        charset = "utf8"
    }
}
