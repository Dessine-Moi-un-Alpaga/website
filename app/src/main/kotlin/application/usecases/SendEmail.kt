package com.dessinemoiunalpaga.website.application.usecases

import com.dessinemoiunalpaga.website.domain.Email

fun interface SendEmail {

    /**
     * @throws UnexpectedEmailException when an error occurs trying to send an email
     */
    suspend fun send(email: Email)
}

class UnexpectedEmailException(cause: Throwable?) : Exception(cause)
