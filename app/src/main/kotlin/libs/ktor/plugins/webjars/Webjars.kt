/*
 * Copyright 2014-2024 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package be.alpago.website.libs.ktor.plugins.webjars

import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.CachingOptions
import io.ktor.http.content.EntityTagVersion
import io.ktor.http.content.LastModifiedVersion
import io.ktor.http.content.caching
import io.ktor.http.content.versions
import io.ktor.http.defaultForFilePath
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.http.content.StaticFileLocationProperty
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.util.date.GMTDate
import org.webjars.WebJarVersionLocator
import kotlin.time.Duration.Companion.days

private val MAX_AGE = 90.days.inWholeSeconds.toInt()

/**
 * Temporary workaround for https://youtrack.jetbrains.com/issue/KTOR-6858
 */
val Webjars: ApplicationPlugin<WebjarsConfig> = createApplicationPlugin("Webjars", ::WebjarsConfig) {
    val webjarsPrefix = pluginConfig.path
    val locator = WebJarVersionLocator()
    val lastModifiedVersion = GMTDate()

    onCall { call ->
        if (call.response.isCommitted) return@onCall

        val fullPath = call.request.path()

        if (!fullPath.startsWith(webjarsPrefix) ||
            call.request.httpMethod != HttpMethod.Get ||
            fullPath.last() == '/'
        ) {
            return@onCall
        }

        val resourcePath = fullPath.removePrefix(webjarsPrefix)

        try {
            call.attributes.put(StaticFileLocationProperty, resourcePath)
            val firstDelimiter = if (resourcePath.startsWith("/")) 1 else 0
            val nextDelimiter = resourcePath.indexOf("/", 1)
            val webjar = if (nextDelimiter > -1) resourcePath.substring(firstDelimiter, nextDelimiter) else ""
            val partialPath = resourcePath.substring(nextDelimiter + 1)
            val version = locator.version(webjar) ?: throw IllegalArgumentException("jar $webjar not found")
            val fullPath = locator.fullPath(webjar, partialPath) ?: throw IllegalArgumentException("jar $webjar not found")
            val stream = WebjarsConfig::class.java.classLoader.getResourceAsStream(fullPath) ?: return@onCall
            val content = InputStreamContent(stream, ContentType.defaultForFilePath(fullPath)).apply {
                EntityTagVersion(version)
                versions += LastModifiedVersion(lastModifiedVersion)
                versions += EntityTagVersion(version)
                caching = CachingOptions(CacheControl.MaxAge(MAX_AGE))
            }
            call.respond(content)
        } catch (_: IllegalArgumentException) {
        }
    }
}
