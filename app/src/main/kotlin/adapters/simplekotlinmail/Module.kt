package be.alpago.website.adapters.simplekotlinmail

import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.libs.di.getEnvironmentVariable
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import net.axay.simplekotlinmail.delivery.mailerBuilder
import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.api.mailer.config.TransportStrategy

fun Application.simpleKotlinMail() {
    dependencies {
        provide {
            SimpleKotlinMailProperties(
                address = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
                smtpServerAddress = getEnvironmentVariable("DMUA_SMTP_SERVER_ADDRESS"),
                smtpServerPassword = getEnvironmentVariable("DMUA_SMTP_SERVER_PASSWORD"),
                smtpServerPort = getEnvironmentVariable("DMUA_SMTP_SERVER_PORT").toInt(),
                smtpServerUsername = getEnvironmentVariable("DMUA_SMTP_SERVER_USERNAME"),
            )
        }

        provide {
            mailerBuilder(
                host = resolve<SimpleKotlinMailProperties>().smtpServerAddress,
                password = resolve<SimpleKotlinMailProperties>().smtpServerPassword,
                port = resolve<SimpleKotlinMailProperties>().smtpServerPort,
                username = resolve<SimpleKotlinMailProperties>().smtpServerUsername,
            ) {
                withTransportStrategy(TransportStrategy.SMTP_TLS)
            }
        }

        provide<SendEmail> {
            SimpleKotlinMailService(
                mailer = resolve<Mailer>(),
                properties = resolve<SimpleKotlinMailProperties>(),
            )
        }
    }
}
