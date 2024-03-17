package be.alpago.website.interfaces.kotlinx.html.header

import be.alpago.website.application.PageModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.header.navigation.navigationMenu
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.section

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
