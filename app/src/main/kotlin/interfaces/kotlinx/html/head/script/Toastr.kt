package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.toastr() {
    script {
        async = true
        attributes["referrerpolicy"] = "no-referrer"
        crossorigin = ScriptCrossorigin.anonymous
        integrity = "sha512-lbwH47l/tPXJYG9AcFNoJaTMhGvYWhVM9YI43CT+uteTRRaiLCui8snIgyAN8XWgNjNhCqlAUdzZptso6OCoFQ=="
        src = "https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"
    }
}
