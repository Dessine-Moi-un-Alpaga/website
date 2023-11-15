package be.alpago.website.interfaces.kotlinx.html.head.script

import be.alpago.website.application.PageModel
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
