package be.alpago.website.libs.page.template.head.script

import be.alpago.website.libs.page.model.PageModel
import kotlinx.html.HEAD

fun HEAD.scripts(pageModel: PageModel) {
    photoswipe(pageModel)
    jquery()
    responsiveTools()
    dropotron()
    escapeVelocity()
    toastr()
    email()
}
