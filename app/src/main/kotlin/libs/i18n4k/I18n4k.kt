package be.alpago.website.libs.i18n4k

import de.comahe.i18n4k.Locale
import de.comahe.i18n4k.config.I18n4kConfigImmutable
import de.comahe.i18n4k.i18n4k

fun setLocale() {
    i18n4k = I18n4kConfigImmutable()
        .withLocale(Locale.FRENCH)
}
