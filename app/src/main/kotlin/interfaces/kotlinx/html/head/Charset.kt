package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head

import kotlinx.html.*

fun HEAD.charset() {
    meta {
        charset = Charsets.UTF_8.name()
    }
}
