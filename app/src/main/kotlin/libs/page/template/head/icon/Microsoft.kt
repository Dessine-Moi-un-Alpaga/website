package be.alpago.website.libs.page.template.head.icon

import kotlinx.html.*

fun HEAD.microsoftIcons() {
    meta {
        name = "msapplication-TileColor"
        content = "white"
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
