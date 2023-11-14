package be.alpago.website.libs.firestore

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
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

    client.plugin(HttpSend).intercept { request ->
        if (request.url.host == FIRESTORE_HOSTNAME) {
            var token = tokenStorage.get()

            if (token != null) {
                // try with existing token
                request.bearerAuth(token)
                val call = execute(request)

                if (call.response.status == HttpStatusCode.Forbidden) {
                    // token has expired
                    request.headers {
                        remove(HttpHeaders.Authorization)
                    }

                    token = null
                }
            }

            if (token == null) {
                // get new token
                val response = client.request(GOOGLE_METADATA_ENDPOINT) {
                    headers {
                        header("Metadata-Flavor", "Google")
                    }
                }

                if (response.status.isSuccess()) {
                    token = response.body<GoogleToken>().value
                    tokenStorage.set(token)
                }
            }

            if (token != null) {
                request.bearerAuth(token)
            }
        }

        execute(request)
    }

    return client
}

@Serializable
private data class GoogleToken(
    @SerialName("access_token") val value: String
)
