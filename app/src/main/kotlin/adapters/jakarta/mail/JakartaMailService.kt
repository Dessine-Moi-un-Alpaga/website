package be.alpago.website.adapters.jakarta.mail

import be.alpago.website.adapters.simplekotlinmail.Messages
import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.domain.Email
import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties

class JakartaMailService(
    private val properties: JakartaMailProperties,
) : SendEmail {

    override suspend fun send(email: Email) {
        val configuration = Properties()
        configuration["mail.smtp.auth"] = true
        configuration["mail.smtp.starttls.enable"] = true
        configuration["mail.smtp.host"] = properties.smtpServerAddress
        configuration["mail.smtp.port"] = properties.smtpServerPort

        val session = Session.getInstance(configuration, object : Authenticator() {
            protected override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(properties.smtpServerUsername, properties.smtpServerPassword)
            }
        })

        val message = MimeMessage(session)
        message.setFrom(properties.smtpServerUsername)
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(properties.address))
        message.setSubject(Messages.emailSubject("${email.name} (${email.from})"))
        message.setText(email.message)
        Transport.send(message)
    }
}
