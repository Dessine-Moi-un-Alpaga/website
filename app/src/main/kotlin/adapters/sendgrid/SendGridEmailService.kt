package be.alpago.website.adapters.sendgrid

import be.alpago.website.application.EmailService
import be.alpago.website.application.InvalidEmailException
import be.alpago.website.application.UnexpectedEmailException
import be.alpago.website.domain.Email
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

private const val ENDPOINT = "https://api.sendgrid.com/v3/mail/send"

data class SendGridProperties(
    val address: String,
    val apiKey: String,
)

class SendGridEmailService(
    private val properties: SendGridProperties,
) : EmailService {

    override suspend fun send(email: Email) {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }.use {
            val response = it.post(ENDPOINT) {
                contentType(ContentType.Application.Json)
                headers {
                    bearerAuth(properties.apiKey)
                }
                setBody(
                    email.body(
                        subject = Messages.emailSubject(email.name),
                        to = properties.address
                    )
                )
            }

            if (response.status == HttpStatusCode.BadRequest) {
                throw InvalidEmailException()
            } else if (!response.status.isSuccess()) {
                throw UnexpectedEmailException("$response")
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
