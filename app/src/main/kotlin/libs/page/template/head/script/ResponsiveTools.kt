package be.alpago.website.libs.page.template.head.script

import kotlinx.html.*

fun HEAD.responsiveTools() {
    script {
        src = "/webjars/responsive-tools/dist/browser.min.js"
    }
    script {
        src = "/webjars/responsive-tools/dist/breakpoints.min.js"
    }
}
