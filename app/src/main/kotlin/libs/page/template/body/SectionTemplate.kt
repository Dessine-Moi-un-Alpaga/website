package be.alpago.website.libs.page.template.body

import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.page.model.SectionModel
import be.alpago.website.libs.page.template.style.EscapeVelocity
import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class SectionTemplate(sectionModel: SectionModel) : Template<FlowContent> {

    private val id = sectionModel.id
    private val color = sectionModel.color

    val pageTitle = Placeholder<FlowContent>()
    val content = Placeholder<FlowContent>()

    override fun FlowContent.apply() {
        section {
            classes = setOf(
                EscapeVelocity.wrapper,
                color.toStyle()
            )
            id = this@SectionTemplate.id

            div {
                classes = setOf(EscapeVelocity.title)

                insert(pageTitle)
            }

            div {
                classes = setOf(EscapeVelocity.container)

                insert(content)
            }
        }
    }
}

private fun SectionColor.toStyle() = when (this) {
    SectionColor.GREY -> EscapeVelocity.style3
    SectionColor.RED -> EscapeVelocity.style1
    SectionColor.WHITE -> EscapeVelocity.style2
}
