package be.alpago.website.adapters

import be.alpago.website.adapters.persistence.firestore.firestore
import be.alpago.website.adapters.email.jakarta.mail.jakartaMail
import io.ktor.server.application.Application

fun Application.adapters() {
    firestore()
    jakartaMail()
}
