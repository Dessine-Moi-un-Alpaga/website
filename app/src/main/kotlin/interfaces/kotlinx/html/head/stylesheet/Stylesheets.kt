package be.alpago.website.interfaces.kotlinx.html.head.stylesheet

import be.alpago.website.application.PageModel
import kotlinx.html.*

fun HEAD.stylesheets(pageModel: PageModel) {
    fontAwesomeStylesheets()
    escapeVelocityStylesheet()
    photoswipeStylesheet(pageModel)
    toastrStylesheet()
    customStylesheets()
}
