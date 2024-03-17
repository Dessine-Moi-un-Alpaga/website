package be.alpago.website.interfaces.kotlinx.html

import be.alpago.website.application.PageModel
import be.alpago.website.interfaces.kotlinx.html.body.BodyTemplate
import be.alpago.website.interfaces.kotlinx.html.head.HeadTemplate
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class LayoutTemplate(
    private val properties: TemplateProperties,
    private val pageModel: PageModel
) : Template<HTML> {

    override fun HTML.apply() {
        lang = "fr"

        insert(HeadTemplate(pageModel)) { }
        insert(BodyTemplate(pageModel, properties)) { }
    }
}
