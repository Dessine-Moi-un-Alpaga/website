package be.alpago.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.toastr() {
    script {
        async = true
        src = "/webjars/toastr/build/toastr.min.js"
    }
}
