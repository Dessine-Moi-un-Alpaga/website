package be.alpago.website.application.usecases

import be.alpago.website.domain.Email

fun interface SendEmail {

    /**
     * @throws UnexpectedEmailException when an error occurs trying to send an email
     */
    suspend fun send(email: Email)
}

class UnexpectedEmailException(cause: Throwable?) : Exception(cause)
