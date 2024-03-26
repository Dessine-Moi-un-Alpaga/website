package be.alpago.website.interfaces.kotlinx.html

import be.alpago.website.libs.di.getEnvironmentVariable
import be.alpago.website.libs.di.register

fun templates() {
    templateProperties()
}

private fun templateProperties() {
    register {
        TemplateProperties(
            baseAssetUrl = getEnvironmentVariable("DMUA_BASE_ASSET_URL"),
            emailAddress = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
            includeTestIds = getEnvironmentVariable("DMUA_TEST", "false").toBoolean()
        )
    }
}
