package be.alpago.website.libs.page.template.head.icon

import kotlinx.html.HEAD
import kotlinx.html.link

private const val APPLE_TOUCH_ICON = "apple-touch-icon"

private val APPLE_TOUCH_FAVICON_SIZES = listOf(
    180, 152, 144, 120, 114, 72, 60
)

fun HEAD.appleIcons() {
    link {
        href = "/assets/icons/favicon-57.png"
        rel = APPLE_TOUCH_ICON
    }

    for (size in APPLE_TOUCH_FAVICON_SIZES) {
        appleTouchFavicon(size)
    }
}

private fun HEAD.appleTouchFavicon(size: Int) {
    link {
        href = "/assets/icons/favicon-${size}.png"
        rel = APPLE_TOUCH_ICON
        sizes = "${size}x${size}"
    }
}
