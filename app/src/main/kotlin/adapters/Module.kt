package com.dessinemoiunalpaga.website.adapters

import com.dessinemoiunalpaga.website.adapters.email.jakarta.mail.jakartaMail
import com.dessinemoiunalpaga.website.adapters.persistence.firestore.firestore
import io.ktor.server.application.Application

internal fun Application.adapters() {
    firestore()
    jakartaMail()
}
