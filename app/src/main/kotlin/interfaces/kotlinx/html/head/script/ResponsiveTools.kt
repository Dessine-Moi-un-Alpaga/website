package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.responsiveTools() {
    script {
        src = "/assets/js/responsive-tools/browser.min.js"
    }
    script {
        src = "/assets/js/responsive-tools/breakpoints.min.js"
    }
}
