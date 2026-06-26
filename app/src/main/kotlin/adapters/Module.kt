package be.alpago.website.adapters

import be.alpago.website.adapters.email.jakarta.mail.jakartaMail
import be.alpago.website.adapters.persistence.firestore.firestore
import io.ktor.server.application.Application

internal fun Application.adapters() {
    firestore()
    jakartaMail()
}
