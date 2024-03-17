package be.alpago.website.interfaces.kotlinx.html.head

import be.alpago.website.application.PageModel
import be.alpago.website.interfaces.kotlinx.html.head.icon.icons
import be.alpago.website.interfaces.kotlinx.html.head.script.scripts
import be.alpago.website.interfaces.kotlinx.html.head.stylesheet.stylesheets
import io.ktor.server.html.Template
import kotlinx.html.*

class HeadTemplate(private val pageModel: PageModel) : Template<HTML> {

    override fun HTML.apply() {
        head {
            title {
                +pageModel.title
            }

            meta {
                name = "description"
                content = pageModel.description
            }

            charset()
            viewport()
            scripts(pageModel)
            stylesheets(pageModel)
            icons()
        }
    }
}
