package be.alpago.website.libs.page.template.head.script

import kotlinx.html.HEAD
import kotlinx.html.script

fun HEAD.toastr() {
    script {
        async = true
        src = "/webjars/toastr/build/toastr.min.js"
    }
}
