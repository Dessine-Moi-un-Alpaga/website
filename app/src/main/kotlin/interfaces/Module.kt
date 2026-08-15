package com.dessinemoiunalpaga.website.interfaces

import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.templates
import com.dessinemoiunalpaga.website.interfaces.ktor.ktor
import io.ktor.server.application.Application

internal suspend fun Application.interfaces() {
    templates()
    ktor()
}
