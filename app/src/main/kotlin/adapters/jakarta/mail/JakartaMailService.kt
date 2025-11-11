package be.alpago.website.adapters.jakarta.mail

import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.domain.Email
import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties

class JakartaMailService(
    private val properties: JakartaMailProperties,
) : SendEmail {

    override suspend fun send(email: Email) {
        val session = Session.getInstance(configuration())
        val message = message(session, email)
        Transport.send(
            message,
            properties.smtpServerUsername,
            properties.smtpServerPassword
        )
    }

    private fun configuration() = Properties().apply {
        this["mail.smtp.auth"] = true
        this["mail.smtp.starttls.enable"] = true
        this["mail.smtp.host"] = properties.smtpServerAddress
        this["mail.smtp.port"] = properties.smtpServerPort
    }

    private fun message(session: Session, email: Email) = MimeMessage(session).apply {
        this.setFrom(properties.smtpServerUsername)
        this.setRecipients(Message.RecipientType.TO, InternetAddress.parse(properties.address))
        this.subject = Messages.emailSubject("${email.name} (${email.from})")
        this.setText(email.message)
    }
}
