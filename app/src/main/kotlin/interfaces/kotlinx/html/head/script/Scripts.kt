package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import com.dessinemoiunalpaga.website.application.PageModel
import kotlinx.html.*

fun HEAD.scripts(pageModel: PageModel) {
    photoswipe(pageModel)
    jquery()
    responsiveTools()
    dropotron()
    escapeVelocity()
    toastr()
    email()
}
