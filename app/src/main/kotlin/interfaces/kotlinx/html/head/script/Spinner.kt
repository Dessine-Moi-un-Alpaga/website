package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import kotlinx.html.HEAD
import kotlinx.html.script

fun HEAD.spinner() {
    script {
        async = true
        src = "/assets/js/spin.js/spin.js"
    }
}
