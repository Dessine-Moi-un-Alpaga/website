package com.dessinemoiunalpaga.website.adapters.email.jakarta.mail

import com.dessinemoiunalpaga.website.application.usecases.SendEmail
import com.dessinemoiunalpaga.website.application.usecases.MailException
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
import kotlinx.coroutines.withContext
import java.util.Properties

class JakartaMailService(
    private val properties: JakartaMailProperties,
) : SendEmail {

    private val session: Session by lazy {
        Session.getInstance(configuration())
    }

    override suspend fun send(email: Email) {
        withContext(Dispatchers.IO) {
            val message = message(session, email)

            try {
                Transport.send(
                    message,
                    properties.smtpServerUsername,
                    properties.smtpServerPassword
                )
            } catch (e: MessagingException) {
                throw MailException(e)
            }
        }
    }

    private fun configuration() = Properties().apply {
        set("mail.smtp.auth", true)
        set("mail.smtp.starttls.enable", true)
        set("mail.smtp.host", properties.smtpServerAddress)
        set("mail.smtp.port", properties.smtpServerPort)
    }

    private fun message(session: Session, email: Email) = MimeMessage(session).apply {
        setFrom(properties.smtpServerUsername)
        setRecipients(Message.RecipientType.TO, InternetAddress.parse(properties.address))
        subject = Messages.emailSubject("${email.sender.name} (${email.sender.address})")
        setText(email.message)
    }
}
