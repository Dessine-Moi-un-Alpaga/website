package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.header

import com.dessinemoiunalpaga.website.application.PageModel
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.header.navigation.navigationMenu
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
import kotlinx.html.*

fun DIV.header(
    properties: TemplateProperties,
    pageModel: PageModel,
) {

    section {
        classes = setOf(EscapeVelocity.wrapper)
        id = EscapeVelocity.header

        logo(properties)
        navigationMenu(pageModel)
    }
}
