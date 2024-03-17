package be.alpago.website.interfaces.kotlinx.html.header

import be.alpago.website.application.PageModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.header.navigation.navigationMenu
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
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
