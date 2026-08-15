package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import com.dessinemoiunalpaga.website.application.PageModel
import kotlinx.html.*

fun HEAD.stylesheets(pageModel: PageModel) {
    fontAwesomeStylesheets()
    escapeVelocityStylesheet()
    photoswipeStylesheet(pageModel)
    toastrStylesheet()
    customStylesheets()
}
