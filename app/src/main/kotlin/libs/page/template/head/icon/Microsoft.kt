package be.alpago.website.libs.page.template.head.icon

import kotlinx.css.Color
import kotlinx.html.HEAD
import kotlinx.html.meta

fun HEAD.microsoftIcons() {
    meta {
        name = "msapplication-TileColor"
        content = "${Color.white}"
    }

    meta {
        name = "msapplication-TileImage"
        content = "/assets/icons/favicon-144.png"
    }

    meta {
        name = "msapplication-config"
        content = "/assets/icons/browserconfig.xml"
    }
}
