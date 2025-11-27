package be.alpago.website.libs.kotlin.i18n

private val SUFFIXES = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")

fun ordinal(i: Int) = when (i % 100) {
    11, 12, 13 -> "${i}th"
    else -> "${i}${SUFFIXES[i % 10]}"
}
