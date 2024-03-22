package be.alpago.website.adapters.firestore

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.atomic.AtomicReference

private const val FIRESTORE_HOSTNAME = "firestore.googleapis.com"
private const val GOOGLE_METADATA_ENDPOINT = "http://metadata.google.internal/computeMetadata/v1/instance/service-accounts/default/token"
private const val GOOGLE_METADATA_HEADER_NAME = "Metadata-Flavor"
private const val GOOGLE_METADATA_HEADER_VALUE = "Google"

fun createNewHttpClient(): HttpClient {

    val tokenStorage = AtomicReference<BearerTokens>()

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(DefaultRequest) {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
        }
        install(Auth) {
            bearer {
                loadTokens {
                    tokenStorage.get()
                }
                refreshTokens {
                    val response = client.request(GOOGLE_METADATA_ENDPOINT) {
                        headers {
                            header(GOOGLE_METADATA_HEADER_NAME, GOOGLE_METADATA_HEADER_VALUE)
                        }
                        markAsRefreshTokenRequest()
                    }

                    if (response.status.isSuccess()) {
                        val token = response.body<GoogleToken>().value
                        val tokens = BearerTokens(token, token)
                        tokenStorage.set(tokens)
                    }

                    tokenStorage.get()
                }
                sendWithoutRequest { request ->
                    request.url.host == FIRESTORE_HOSTNAME
                }
            }
        }
    }

    return client
}

