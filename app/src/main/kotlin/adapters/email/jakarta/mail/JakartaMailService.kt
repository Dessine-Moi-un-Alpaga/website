package com.dessinemoiunalpaga.website.adapters.email.jakarta.mail

import com.dessinemoiunalpaga.website.application.usecases.SendEmail
import com.dessinemoiunalpaga.website.application.usecases.UnexpectedEmailException
import com.dessinemoiunalpaga.website.domain.Email
import com.dessinemoiunalpaga.website.i18n.Messages
import jakarta.mail.Message
import jakarta.mail.MessagingException
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Properties

class JakartaMailService(
    private val properties: JakartaMailProperties,
) : SendEmail {

    private val session: Session by lazy {
        Session.getInstance(configuration())
    }

    override suspend fun send(email: Email) {
        coroutineScope {
            launch(Dispatchers.IO) {
                val message = message(session, email)

                try {
                    Transport.send(
                        message,
                        properties.smtpServerUsername,
                        properties.smtpServerPassword
                    )
                } catch (e: MessagingException) {
                    throw UnexpectedEmailException(e)
                }
            }
        }
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
        this.subject = Messages.emailSubject("${email.sender.name} (${email.sender.address})")
        this.setText(email.message)
    }
}
