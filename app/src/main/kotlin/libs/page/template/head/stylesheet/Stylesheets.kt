package be.alpago.website.libs.page.template.head.stylesheet

import be.alpago.website.libs.page.model.PageModel
import kotlinx.html.*

fun HEAD.stylesheets(pageModel: PageModel) {
    fontAwesomeStylesheets()
    escapeVelocityStylesheet()
    photoswipeStylesheet(pageModel)
    toastrStylesheet()
    customStylesheets()
}
