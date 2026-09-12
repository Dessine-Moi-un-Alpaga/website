package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import kotlinx.html.*

fun HEAD.dropotron() {
    script {
        crossorigin = ScriptCrossorigin.anonymous
        integrity = "sha512-ugEhUBPC3XfTEBbRia5d9er1tFe5N4yzwQr3xrNSTfmT09xe0fwYxgfDSLwUKCnFoFtLd5rJBZP5tdfcUzLNvw=="
        noReferrer()
        src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.dropotron/1.4.3/jquery.dropotron.min.js"
    }
}
