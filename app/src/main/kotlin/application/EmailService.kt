package be.alpago.website.application

import be.alpago.website.domain.Email

interface EmailService {

    suspend fun send(email: Email)
}
