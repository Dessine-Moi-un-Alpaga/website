package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.jquery() {
    script {
        crossorigin = ScriptCrossorigin.anonymous
        integrity = "sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
        noReferrer()
        src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
    }
}
