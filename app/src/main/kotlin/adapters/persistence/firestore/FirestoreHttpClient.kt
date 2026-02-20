package be.alpago.website.adapters.persistence.firestore

import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.Sender
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.concurrent.atomic.AtomicReference

private const val FIRESTORE_HOSTNAME = "firestore.googleapis.com"
private const val GOOGLE_METADATA_ENDPOINT = "http://metadata.google.internal/computeMetadata/v1/instance/service-accounts/default/token"
private const val GOOGLE_METADATA_HEADER_NAME = "Metadata-Flavor"
private const val GOOGLE_METADATA_HEADER_VALUE = "Google"

fun createHttpClient(): HttpClient {

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
    }

    val tokenStorage = AtomicReference<String>()

    suspend fun Sender.tryExistingToken(request: HttpRequestBuilder): HttpClientCall? {
        var result: HttpClientCall? = null
        val token = tokenStorage.get()

        if (token != null) {
            request.bearerAuth(token)
            result = execute(request)

            if (result.response.status.isAuthenticationRelated()) {
                result = null
            }
        }

        return result
    }

    suspend fun getNewToken(): String? {
        var result: String? = null
        val response = client.request(GOOGLE_METADATA_ENDPOINT) {
            headers {
                header(GOOGLE_METADATA_HEADER_NAME, GOOGLE_METADATA_HEADER_VALUE)
            }
        }

        if (response.status.isSuccess()) {
            result = response.body<GoogleToken>().value
        }

        return result
    }

    suspend fun Sender.interceptFirestoreRequest(request: HttpRequestBuilder): HttpClientCall? {
        val result = tryExistingToken(request)

        if (result == null) {
            val token = getNewToken()

            if (token != null) {
                tokenStorage.set(token)
                request.bearerAuth(token)
            }
        }

        return result
    }

    client.plugin(HttpSend).intercept { request ->
        var result: HttpClientCall? = null

        if (request.url.host == FIRESTORE_HOSTNAME) {
            result = interceptFirestoreRequest(request)
        }

        result ?: execute(request)
    }

    return client
}

@Serializable
private data class GoogleToken(
    @SerialName("access_token") val value: String
)

private fun HttpStatusCode.isAuthenticationRelated() = this == HttpStatusCode.Unauthorized || this == HttpStatusCode.Forbidden
