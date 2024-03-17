package be.alpago.website.interfaces.kotlinx.html.head.script

import kotlinx.html.HEAD
import kotlinx.html.script

fun HEAD.responsiveTools() {
    script {
        src = "/webjars/responsive-tools/dist/browser.min.js"
    }
    script {
        src = "/webjars/responsive-tools/dist/breakpoints.min.js"
    }
}
