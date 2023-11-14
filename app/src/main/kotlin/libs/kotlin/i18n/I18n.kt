package be.alpago.website.libs.kotlin.i18n

import java.util.Locale

fun capitalize(text: Any) = "$text".replaceFirstChar { firstChar ->
    if (firstChar.isLowerCase()) {
        firstChar.titlecase(Locale.FRENCH)
    } else {
        "$firstChar"
    }
}
