package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.escapeVelocity() {
    script {
        src = "/assets/js/escape-velocity/util.js"
    }
    script {
        async = true
        src = "/assets/js/escape-velocity/main.js"
    }
}
