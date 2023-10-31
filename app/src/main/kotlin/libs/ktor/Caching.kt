package be.alpago.website.libs.ktor

import io.ktor.http.CacheControl
import io.ktor.http.HttpMethod
import io.ktor.http.content.CachingOptions
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.request.httpMethod

private const val MAX_AGE_SECONDS = 60 * 15

fun Application.caching() {
    install(CachingHeaders) {
        options { call, _ ->
            when (call.request.httpMethod) {
                HttpMethod.Get -> CachingOptions(CacheControl.MaxAge(MAX_AGE_SECONDS))
                else -> CachingOptions(CacheControl.NoCache(CacheControl.Visibility.Public))
            }
        }
    }
}
