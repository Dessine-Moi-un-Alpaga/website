package be.alpago.website.interfaces.kotlinx.html.head.icon

import kotlinx.html.HEAD
import kotlinx.html.meta

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
