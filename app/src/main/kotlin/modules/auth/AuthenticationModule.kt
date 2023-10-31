package be.alpago.website.modules.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import be.alpago.website.libs.environment.Environment
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.Principal
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import org.koin.ktor.ext.inject

fun Application.authentication() {
    val environment by inject<Environment>()
    val allowedCredentials = parseCredentials(environment)
    val verifyer = BCrypt.verifyer()

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

private fun parseCredentials(environment: Environment): Map<String, String> {
    return environment.credentials.split("\n")
        .filter { it.isNotBlank() }
        .map { it.split(":") }
        .associate { it[0] to it[1] }
}
