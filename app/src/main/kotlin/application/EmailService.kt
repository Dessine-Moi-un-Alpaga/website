package be.alpago.website.application

import be.alpago.website.domain.Email

fun interface EmailService {

    suspend fun send(email: Email)
}
