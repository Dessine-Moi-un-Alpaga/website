package be.alpago.website.modules.email

import be.alpago.website.libs.environment.Environment
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun sendGridEmailService() = module {
    single<EmailService> {
        val environment by inject<Environment>()
        SendGridEmailService(environment)
    }
}

fun Application.email() {
    koin {
        modules(sendGridEmailService())
    }

    emailRoute()
}
