package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.sendgrid.SendGridEmailService
import be.alpago.website.adapters.sendgrid.SendGridProperties
import be.alpago.website.application.usecases.SendEmail
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun Application.emailModule() {
    koin {
        modules(
            module {
                single<SendEmail> {
                    val properties = SendGridProperties(
                        address = getProperty("DMUA_EMAIL_ADDRESS"),
                        apiKey = getProperty("DMUA_SEND_GRID_API_KEY"),
                    )
                    SendGridEmailService(properties)
                }
            }
        )
    }
}
