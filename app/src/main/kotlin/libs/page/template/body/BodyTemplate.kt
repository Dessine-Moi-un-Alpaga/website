package be.alpago.website.libs.page.template.body

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.template.footer.footer
import be.alpago.website.libs.page.template.header.header
import be.alpago.website.libs.page.template.style.EscapeVelocity
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id

class BodyTemplate(
    private val environment: Environment,
    private val pageModel: PageModel,
) : Template<HTML> {

    override fun HTML.apply() {
        body {
            classes = setOf(EscapeVelocity.isPreload)

            div {
                id = EscapeVelocity.pageWrapper

                header(environment, pageModel)

                for (section in pageModel.sections) {
                    val template = SectionTemplateFactory.createSectionTemplate(environment, section)
                    insert(template) { }
                }

                footer(environment)
            }
        }
    }
}
