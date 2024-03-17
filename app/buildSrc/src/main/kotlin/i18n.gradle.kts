import java.util.Locale

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("de.comahe.i18n4k")
}

i18n4k {
    sourceCodeLocales = listOf(
        Locale.FRENCH.language,
        Locale.ENGLISH.language
    )
}
