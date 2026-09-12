package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.*

fun HEAD.toastrStylesheet() {
    link {
        externalStylesheetAttributes()
        integrity = "sha512-6S2HWzVFxruDlZxI3sXOZZ4/eJ8AcxkQH1+JjSe/ONCEqR9L4Ysq5JdT5ipqtzU7WHalNwzwBv+iE51gNHJNqQ=="
        rel = LinkRel.stylesheet
        href = "https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"
    }
}
