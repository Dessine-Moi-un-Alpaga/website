package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.PageModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.footer.footer
import be.alpago.website.interfaces.kotlinx.html.header.header
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class BodyTemplate(
    private val model: PageModel,
    private val properties: TemplateProperties,
) : Template<HTML> {

    override fun HTML.apply() {
        body {
            classes = setOf(EscapeVelocity.isPreload)

            div {
                id = EscapeVelocity.pageWrapper

                header(properties, model)

                for (section in model.sections) {
                    val template = SectionTemplateFactory.createSectionTemplate(properties, section)
                    insert(template) { }
                }

                footer(properties)
            }
        }
    }
}
