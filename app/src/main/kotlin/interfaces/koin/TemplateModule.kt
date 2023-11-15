package be.alpago.website.interfaces.koin

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun Application.templateModule() {
    koin {
        modules(
            module {
                single<TemplateProperties> {
                    TemplateProperties(
                        baseAssetUrl = getProperty("DMUA_BASE_ASSET_URL"),
                        emailAddress = getProperty("DMUA_EMAIL_ADDRESS")
                    )
                }
            }
        )
    }
}
