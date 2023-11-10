package be.alpago.website.modules.email

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.i18n.Messages
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

private const val ENDPOINT = "https://api.sendgrid.com/v3/mail/send"

class SendGridEmailService(
    private val environment: Environment,
) : EmailService {

    override suspend fun send(email: Email) {
        HttpClient(CIO) {
            expectSuccess = true

            install(ContentNegotiation) {
                json()
            }
        }.use {
            it.post(ENDPOINT) {
                contentType(ContentType.Application.Json)
                headers {
                    bearerAuth(environment.sendGridApiKey)
                }
                setBody(
                    email.body(
                        subject = Messages.emailSubject(email.name),
                        to = environment.emailAddress
                    )
                )
            }
        }
    }
}

private fun Email.body(subject: String, to: String) = SendGridEmail(
    content = listOf(
        SendGridContent(
            type = "${ContentType.Text.Plain}",
            value = message
    )   ,
    ),
    from = SendGridSender(
        email = from,
        name = name
    ),
    personalizations = listOf(
        SendGridPersonalization(
            listOf(
                SendGridRecipient(to)
            ),
        ),
    ),
    subject
)

@Serializable
private data class SendGridEmail(
    val content: List<SendGridContent>,
    val from: SendGridSender,
    val personalizations: List<SendGridPersonalization>,
    val subject: String,
)

@Serializable
private data class SendGridPersonalization(
    val to: List<SendGridRecipient>,
)

@Serializable
private data class SendGridRecipient(
    val email: String,
)

@Serializable
private data class SendGridSender(
    val email: String,
    val name: String,
)

@Serializable
private data class SendGridContent(
    val type: String,
    val value: String,
)
