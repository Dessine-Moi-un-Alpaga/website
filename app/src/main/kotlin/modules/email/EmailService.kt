package be.alpago.website.modules.email

interface EmailService {

    suspend fun send(email: Email)
}
