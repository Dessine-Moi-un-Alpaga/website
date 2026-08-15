package com.dessinemoiunalpaga.website.interfaces.ktor

import com.dessinemoiunalpaga.website.interfaces.ktor.routes.routes
import io.ktor.server.application.Application

internal suspend fun Application.ktor() {
    assets()
    authentication()
    autoHeadResponses()
    httpCaching()
    routes()
    serialization()
    validation()
    webjars()
}
