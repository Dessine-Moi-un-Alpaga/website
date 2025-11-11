package be.alpago.website.adapters

import be.alpago.website.adapters.firestore.firestore
import be.alpago.website.adapters.jakarta.mail.jakartaMail
import io.ktor.server.application.Application

fun Application.adapters() {
    firestore()
    jakartaMail()
}
