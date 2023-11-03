package be.alpago.website.libs.page.template.header

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.template.header.navigation.navigationMenu
import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.section

fun DIV.header(
    environment: Environment,
    pageModel: PageModel,
) {

    section {
        classes = setOf(EscapeVelocity.wrapper)
        id = EscapeVelocity.header

        logo(environment)
        navigationMenu(pageModel)
    }
}
