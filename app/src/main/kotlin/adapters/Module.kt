package be.alpago.website.adapters

import be.alpago.website.adapters.firestore.firestore
import be.alpago.website.adapters.javamail.javaMail
import io.ktor.server.application.Application

fun Application.adapters() {
    firestore()
    javaMail()
}
