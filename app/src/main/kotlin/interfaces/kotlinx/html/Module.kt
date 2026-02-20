package be.alpago.website.interfaces.kotlinx.html

import be.alpago.website.libs.getEnvironmentVariable
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

internal fun Application.templates() {
    templateProperties()
}

private fun Application.templateProperties() {
    dependencies {
        provide {
            TemplateProperties(
                baseAssetUrl = getEnvironmentVariable("DMUA_BASE_ASSET_URL"),
                emailAddress = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
                includeTestIds = getEnvironmentVariable("DMUA_TEST", "false").toBoolean()
            )
        }
    }

}
