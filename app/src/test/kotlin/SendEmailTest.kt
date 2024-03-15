package be.alpago.website

import be.alpago.website.adapters.sendgrid.SendGridProperties
import be.alpago.website.application.usecases.UnexpectedEmailException
import be.alpago.website.interfaces.i18n4k.i18n
import be.alpago.website.interfaces.ktor.routes.emailRoute
import be.alpago.website.interfaces.ktor.serialization
import be.alpago.website.interfaces.ktor.validation
import be.alpago.website.modules.emailModule
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.ktor.utils.io.ByteReadChannel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SendEmailTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    private suspend fun ApplicationTestBuilder.sendEmailTest(
        from : String = "nobody@nowhere.com",
        name: String = "nobody",
        message: String = "nothing",
        assertions: MockRequestHandleScope.(io.ktor.client.request.HttpRequestData) -> HttpResponseData
    ): HttpResponse {
        val destination = "contact@dessinemoiunalpaga.com"
        val apiKey = "**SECRET**"

        val expected = """
            {
              "content": [
                {
                  "type": "text/plain",
                  "value": "${message}"
                }
              ],
              "from": {
                "email": "${from}",
                "name": "${name}"
              },
              "personalizations": [
                {
                  "to": [
                    {
                      "email": "${destination}"
                    }
                  ]
                }
              ],
              "subject": "[website] Message re\u00e7u de ${name}"
            }
        """.trimIndent()

        application {
            i18n()
            serialization()
            validation()

            emailModule()
            emailRoute()

            mock<HttpClientEngine> {
                MockEngine { request ->
                    request.method shouldBe HttpMethod.Post
                    request.url.host shouldBe "api.sendgrid.com"
                    request.url.encodedPath shouldBe "/v3/mail/send"
                    request.headers.get(HttpHeaders.Authorization) shouldBe "Bearer ${apiKey}"

                    val body = request.body as TextContent
                    body.contentType shouldBe ContentType.Application.Json
                    body.text shouldEqualJson expected

                    assertions.invoke(this, request)
                }
            }

            mock<SendGridProperties> {
                SendGridProperties(
                    address = destination,
                    apiKey
                )
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        return client.post("/api/email") {
            contentType(ContentType.Application.Json)
            setBody("""
                {
                  "from": "${from}",
                  "name": "${name}",
                  "message": "${message}"
                }
            """.trimIndent())
        }
    }

    @Test
    fun `emails can be sent`() = testApplication {
        val response = sendEmailTest {_ ->
            respond(
                content = ByteReadChannel.Empty,
                status = HttpStatusCode.OK,
            )
        }

        response.status shouldBe HttpStatusCode.OK
    }

    @Test
    fun `bad request errors are propagated`() = testApplication {
        val response = sendEmailTest {
            respond(
                content = ByteReadChannel.Empty,
                status = HttpStatusCode.BadRequest,
            )
        }

        response shouldHaveStatus HttpStatusCode.BadRequest
    }

    @Test
    fun `other errors are translated to internal server errors`() = testApplication {
        shouldThrow<UnexpectedEmailException> {
            sendEmailTest {
                respond(
                    content = ByteReadChannel.Empty,
                    status = HttpStatusCode.TooEarly,
                )
            }
        }
    }
}
