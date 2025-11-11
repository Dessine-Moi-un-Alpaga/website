package be.alpago.website.adapters.javamail

import be.alpago.website.libs.getEnvironmentVariable
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.javaMail() {
    dependencies {
        provide {
            JavaMailProperties(
                address = getEnvironmentVariable("DMUA_EMAIL_ADDRESS"),
                smtpServerAddress = getEnvironmentVariable("DMUA_SMTP_SERVER_ADDRESS"),
                smtpServerPassword = getEnvironmentVariable("DMUA_SMTP_SERVER_PASSWORD"),
                smtpServerPort = getEnvironmentVariable("DMUA_SMTP_SERVER_PORT").toInt(),
                smtpServerUsername = getEnvironmentVariable("DMUA_SMTP_SERVER_USERNAME"),
            )
        }

        provide {
            JavaMailService(
                resolve<JavaMailProperties>()
            )
        }
    }
}
