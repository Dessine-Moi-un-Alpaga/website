package be.alpago.website.libs.i18n

import java.util.Locale

fun capitalize(any: Any) = any.toString().replaceFirstChar {
    if (it.isLowerCase()) {
        it.titlecase(Locale.FRENCH)
    } else {
        it.toString()
    }
}
