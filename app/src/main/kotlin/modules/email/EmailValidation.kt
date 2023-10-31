package be.alpago.website.modules.email

import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.routing.Route

fun Route.emailValidation() {
    install(RequestValidation) {
        validate<Email> {
            if (it.isValid()) {
                ValidationResult.Valid
            } else {
                ValidationResult.Invalid("n/a")
            }
        }
    }
}
