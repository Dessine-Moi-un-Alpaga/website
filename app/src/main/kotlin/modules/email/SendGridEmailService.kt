package be.alpago.website.modules.email

import be.alpago.website.libs.environment.Environment
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.objects.Content
import io.ktor.http.ContentType
import com.sendgrid.helpers.mail.Mail as SendGridEmail
import com.sendgrid.helpers.mail.objects.Email as EmailAddress

private const val ENDPOINT = "mail/send"

private val CONTENT_TYPE  = "${ContentType.Text.Plain}"

class SendGridEmailService(
    environment: Environment,
    private val sendGrid: SendGrid
) : EmailService {

    private val subject = environment.emailSubject

    private val to = EmailAddress(environment.emailAddress)

    override fun send(email: Email) {
        val request = Request()
        val from = EmailAddress(email.from)
        val content = Content(CONTENT_TYPE, email.message)
        val message = SendGridEmail(from, subject, to, content)
        request.method = Method.POST
        request.endpoint = ENDPOINT
        request.body = message.build()
        sendGrid.api(request)
    }
}
