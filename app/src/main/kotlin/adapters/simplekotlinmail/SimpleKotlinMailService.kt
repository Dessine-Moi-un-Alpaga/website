package be.alpago.website.adapters.simplekotlinmail

import be.alpago.website.application.usecases.SendEmail
import be.alpago.website.application.usecases.UnexpectedEmailException
import be.alpago.website.domain.Email
import kotlinx.coroutines.runBlocking
import net.axay.simplekotlinmail.delivery.MailerManager
import net.axay.simplekotlinmail.delivery.send
import net.axay.simplekotlinmail.email.emailBuilder
import org.simplejavamail.api.mailer.Mailer

data class SimpleKotlinMailProperties(
    val address: String,
    val smtpServerAddress: String,
    val smtpServerPassword: String,
    val smtpServerPort : Int,
    val smtpServerUsername: String,
)

class SimpleKotlinMailService(
    private val mailer: Mailer,
    private val properties: SimpleKotlinMailProperties
) : AutoCloseable, SendEmail {

    override suspend fun send(email: Email) {
        val mail = emailBuilder {
            from(properties.smtpServerUsername)
            withSubject(Messages.emailSubject("${email.name} (${email.from})"))
            withPlainText(email.message)
            to(properties.address)
        }

        try {
            mail.send(mailer)
        } catch (e: Exception) {
            throw UnexpectedEmailException(e.localizedMessage)
        }
    }

    override fun close() {
        runBlocking {
            MailerManager.shutdownMailers()
        }
    }
}
