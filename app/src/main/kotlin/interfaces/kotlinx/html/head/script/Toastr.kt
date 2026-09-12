package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.toastr() {
    script {
        async = true
        crossorigin = ScriptCrossorigin.anonymous
        integrity = "sha512-lbwH47l/tPXJYG9AcFNoJaTMhGvYWhVM9YI43CT+uteTRRaiLCui8snIgyAN8XWgNjNhCqlAUdzZptso6OCoFQ=="
        noReferrer()
        src = "https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"
    }
}
