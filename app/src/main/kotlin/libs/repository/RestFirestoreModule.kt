package be.alpago.website.libs.repository

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.registerCloseable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin
import java.util.concurrent.atomic.AtomicReference

fun createClient(
    authenticationEnabled: Boolean,
): HttpClient {
    return HttpClient(CIO) {
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

        if (authenticationEnabled) {
            val tokenStorage = AtomicReference<BearerTokens>()

            install(Auth) {
                bearer {
                    loadTokens {
                        tokenStorage.get()
                    }

                    refreshTokens {
                        val token = client.get("http://metadata.google.internal/computeMetadata/v1/instance/service-accounts/default/token") {
                            headers {
                                header("Metadata-Flavor", "Google")
                            }
                            markAsRefreshTokenRequest()
                        }.body<GoogleToken>()
                        val tokens = BearerTokens(token.value, token.value)
                        tokenStorage.set(tokens)
                        tokens
                    }
                    sendWithoutRequest { request ->
                        request.url.host == "firestore.googleapis.com"
                    }
                }
            }
        }
    }
}

fun restFirestoreModule() = module {
    single<HttpClient> {
        val environment by inject<Environment>()
        createClient(environment.firestoreAuthenticationEnabled)
    }
}

fun Application.firestore() {
    koin {
        modules(restFirestoreModule())
    }

    val client by inject<HttpClient>()
    registerCloseable {
        client.close()
    }
}

@Serializable
private data class GoogleToken(
    @SerialName("access_token") val value: String
)
