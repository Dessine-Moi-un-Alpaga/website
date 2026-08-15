package com.dessinemoiunalpaga.website.interfaces.ktor

import com.dessinemoiunalpaga.website.libs.ktor.plugins.webjars.Webjars
import io.ktor.server.application.Application
import io.ktor.server.application.install

internal fun Application.webjars() {
    install(Webjars)
}
