package be.alpago.website.interfaces.kotlinx.html.head.icon

import io.ktor.http.ContentType
import kotlinx.html.HEAD
import kotlinx.html.link


private const val ICON = "icon"

private val FAVICON_SIZES = listOf(
    192, 160, 96, 64, 32, 16
)

fun HEAD.icons() {
    link {
        href = "/assets/icons/favicon.ico"
        rel = ICON
        sizes = "16x16 32x32 64x64"
    }

    for (size in FAVICON_SIZES) {
        favicon(size)
    }

    appleIcons()
    microsoftIcons()
}

private fun HEAD.favicon(size: Int) {
    link {
        href = "/assets/icons/favicon-${size}.png"
        rel = ICON
        sizes = "${size}x${size}"
        type = "${ContentType.Image.PNG}"
    }
}
