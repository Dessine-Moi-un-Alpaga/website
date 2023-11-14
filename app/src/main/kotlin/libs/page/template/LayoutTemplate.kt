package be.alpago.website.libs.page.template

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.template.body.BodyTemplate
import be.alpago.website.libs.page.template.head.HeadTemplate
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class LayoutTemplate(
    private val environment: Environment,
    private val pageModel: PageModel
) : Template<HTML> {

    override fun HTML.apply() {
        lang = "fr"

        insert(HeadTemplate(pageModel)) { }
        insert(BodyTemplate(environment, pageModel)) { }
    }
}
