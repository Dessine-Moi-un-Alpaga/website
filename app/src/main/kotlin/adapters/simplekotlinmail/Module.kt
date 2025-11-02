package be.alpago.website.adapters.simplekotlinmail

import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.interfaces.ktor.registerCloseable
import be.alpago.website.libs.di.getEnvironmentVariable
import be.alpago.website.libs.di.inject
import be.alpago.website.libs.di.register
import io.ktor.server.application.Application
import kotlinx.coroutines.runBlocking
import net.axay.simplekotlinmail.delivery.MailerManager
import net.axay.simplekotlinmail.delivery.mailerBuilder
import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.api.mailer.config.TransportStrategy

fun Application.simpleKotlinMail() {
    register<SimpleKotlinMailProperties> {
        SimpleKotlinMailProperties(
            address = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
            smtpServerAddress = getEnvironmentVariable("DMUA_SMTP_SERVER_ADDRESS"),
            smtpServerPassword = getEnvironmentVariable("DMUA_SMTP_SERVER_PASSWORD"),
            smtpServerPort = getEnvironmentVariable("DMUA_SMTP_SERVER_PORT").toInt(),
            smtpServerUsername = getEnvironmentVariable("DMUA_SMTP_SERVER_USERNAME"),
        )
    }

    register<Mailer> {
        mailerBuilder(
            host = inject<SimpleKotlinMailProperties>().smtpServerAddress,
            password = inject<SimpleKotlinMailProperties>().smtpServerPassword,
            port = inject<SimpleKotlinMailProperties>().smtpServerPort,
            username = inject<SimpleKotlinMailProperties>().smtpServerUsername,
        ) {
            withTransportStrategy(TransportStrategy.SMTP_TLS)
        }
    }

    register<SendEmail> {
        SimpleKotlinMailService(
            mailer = inject<Mailer>(),
            properties = inject<SimpleKotlinMailProperties>(),
        )
    }

    registerCloseable {
        runBlocking {
            MailerManager.shutdownMailers()
        }
    }
}
