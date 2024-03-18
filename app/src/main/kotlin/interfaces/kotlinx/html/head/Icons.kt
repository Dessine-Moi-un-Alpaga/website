package be.alpago.website.interfaces.kotlinx.html.head

import io.ktor.http.ContentType
import kotlinx.html.*


private const val ICON = "icon"

fun HEAD.icons() {
    link {
        href = "/assets/icons/favicon.ico"
        rel = ICON
        sizes = "32x32"
    }

    link {
        href = "/assets/icons/icon.svg"
        rel = ICON
        type = "${ContentType.Image.SVG}"
    }

    link {
        href = "/assets/icons/apple-touch-icon.png"
        rel = "apple-touch-icon"
    }

    link {
        href = "/assets/icons/manifest.webmanifest"
        rel = "manifest"
    }
}
