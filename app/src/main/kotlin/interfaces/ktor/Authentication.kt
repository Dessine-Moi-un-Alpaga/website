package be.alpago.website.interfaces.ktor

import at.favre.lib.crypto.bcrypt.BCrypt
import be.alpago.website.libs.getEnvironmentVariable
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.apikey.apiKey
import io.ktor.server.plugins.di.dependencies

internal fun Application.authentication() {
    authenticationProperties()

    val properties: AuthenticationProperties by dependencies
    val verifyer by lazy { BCrypt.verifyer() }

    install(Authentication) {
        apiKey {
            validate { keyFromHeader ->
                var principal: Any? = null
                val expectedApiKeyHash = properties.apiKeyHash
                val result = verifyer.verify(keyFromHeader.toCharArray(), expectedApiKeyHash)

                if (result.verified) {
                    principal = UserIdPrincipal("admin")
                }

                principal
            }
        }
    }
}

private fun Application.authenticationProperties() {
    dependencies {
        provide {
            AuthenticationProperties(
                apiKeyHash = getEnvironmentVariable("DMUA_API_KEY_HASH"),
            )
        }
    }
}
