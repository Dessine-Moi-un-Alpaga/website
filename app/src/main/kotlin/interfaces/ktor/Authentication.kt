package be.alpago.website.interfaces.ktor

import at.favre.lib.crypto.bcrypt.BCrypt
import be.alpago.website.libs.di.inject
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.Principal
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic

data class AuthenticationProperties(
    val credentials: String
)

fun Application.authentication() {
    val properties by lazy { inject<AuthenticationProperties>() }
    val allowedCredentials by lazy { parseCredentials(properties) }
    val verifyer by lazy { BCrypt.verifyer() }

    install(Authentication) {
        basic {
            validate { credentials ->
                var principal: Principal? = null
                val username = credentials.name
                val expectedHash = allowedCredentials[username]

                if (expectedHash != null) {
                    val password = credentials.password
                    val result = verifyer.verify(password.toCharArray(), expectedHash)

                    if (result.verified) {
                        principal = UserIdPrincipal(username)
                    }
                }

                principal
            }
        }
    }
}

private fun parseCredentials(properties: AuthenticationProperties): Map<String, String> {
    return properties.credentials.split("\n")
        .filter { it.isNotBlank() }
        .map { it.split(":") }
        .associate { it[0] to it[1] }
}
