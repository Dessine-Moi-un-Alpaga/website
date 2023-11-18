package be.alpago.website.application.usecases

import be.alpago.website.domain.Email

fun interface SendEmail {

    suspend fun send(email: Email)
}

class InvalidEmailException : Exception()

class UnexpectedEmailException(message: String) : Exception(message)
