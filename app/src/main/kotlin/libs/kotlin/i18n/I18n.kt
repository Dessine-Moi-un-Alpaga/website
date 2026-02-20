package be.alpago.website.libs.kotlin.i18n

import java.util.Locale

// as recommended there: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/capitalize.html
internal fun capitalize(text: Any) = "$text".replaceFirstChar { firstChar ->
    if (firstChar.isLowerCase()) {
        firstChar.titlecase(Locale.FRENCH)
    } else {
        "$firstChar"
    }
}
