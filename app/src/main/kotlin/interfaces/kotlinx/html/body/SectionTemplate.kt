package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.body

import com.dessinemoiunalpaga.website.application.SectionColor
import com.dessinemoiunalpaga.website.application.SectionModel
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.testId
import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*

class SectionTemplate(
    private val model: SectionModel,
    private val properties: TemplateProperties,
) : Template<FlowContent> {

    val title = Placeholder<FlowContent>()
    val content = Placeholder<FlowContent>()

    override fun FlowContent.apply() {
        section {
            classes = setOf(
                EscapeVelocity.wrapper,
                model.color.toStyle()
            )
            id = model.type

            div {
                classes = setOf(EscapeVelocity.title)

                testId("${model.id}-section-title", properties)
                insert(this@SectionTemplate.title)
            }

            div {
                classes = setOf(EscapeVelocity.container)

                testId(model.id, properties)
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
