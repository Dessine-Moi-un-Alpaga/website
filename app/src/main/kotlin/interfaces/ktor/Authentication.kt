package be.alpago.website.interfaces.ktor

import at.favre.lib.crypto.bcrypt.BCrypt
import be.alpago.website.libs.getEnvironmentVariable
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import io.ktor.server.plugins.di.dependencies

internal fun Application.authentication() {
    authenticationProperties()

    val properties: AuthenticationProperties by dependencies
    val allowedCredentials = parseCredentials(properties)
    val verifyer by lazy { BCrypt.verifyer() }

    install(Authentication) {
        basic {
            validate { credentials ->
                var principal: Any? = null
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

private fun Application.authenticationProperties() {
    dependencies {
        provide {
            AuthenticationProperties(
                credentials = getEnvironmentVariable("DMUA_CREDENTIALS"),
            )
        }
    }
}

private fun parseCredentials(properties: AuthenticationProperties): Map<String, String> {
    return properties.credentials.split("\n")
        .filter { it.isNotBlank() }
        .map { it.split(":") }
        .associate { it[0] to it[1] }
}
