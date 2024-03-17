package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.SectionColor
import be.alpago.website.application.SectionModel
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.interfaces.kotlinx.html.style.testId
import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.section

class SectionTemplate(
    model: SectionModel,
    private val properties: TemplateProperties,
) : Template<FlowContent> {

    private val color = model.color
    private val id = model.id
    private val type = model.type

    val title = Placeholder<FlowContent>()
    val content = Placeholder<FlowContent>()

    override fun FlowContent.apply() {
        section {
            classes = setOf(
                EscapeVelocity.wrapper,
                color.toStyle()
            )
            id = type

            div {
                classes = setOf(EscapeVelocity.title)

                testId("${this@SectionTemplate.id}-section-title", properties)
                insert(this@SectionTemplate.title)
            }

            div {
                classes = setOf(EscapeVelocity.container)

                testId(this@SectionTemplate.id, properties)
                insert(content)
            }
        }
    }
}

private fun SectionColor.toStyle() = when (this) {
    SectionColor.GREY  -> EscapeVelocity.style3
    SectionColor.RED   -> EscapeVelocity.style1
    SectionColor.WHITE -> EscapeVelocity.style2
}
