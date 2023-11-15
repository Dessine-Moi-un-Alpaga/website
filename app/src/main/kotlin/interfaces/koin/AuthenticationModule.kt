package be.alpago.website.interfaces.koin

import be.alpago.website.interfaces.ktor.AuthenticationProperties
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun Application.authenticationModule() {
    koin {
        modules(
            module {
                single<AuthenticationProperties> {
                    AuthenticationProperties(
                        credentials = getProperty("DMUA_CREDENTIALS")
                    )
                }
            }
        )
    }
}
