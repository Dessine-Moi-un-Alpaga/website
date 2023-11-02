package be.alpago.website.modules.i18n

import de.comahe.i18n4k.Locale
import de.comahe.i18n4k.config.I18n4kConfigImmutable
import de.comahe.i18n4k.i18n4k
import de.comahe.i18n4k.messages.formatter.MessageFormatterDefault

fun i18n() {
    i18n4k = I18n4kConfigImmutable(
        defaultLocale = Locale.ENGLISH,
        locale = Locale.FRENCH,
        messageFormatter = MessageFormatterDefault,
        treadBlankStringAsNull = true
    )
}
