/*
 * Temporary workaround for https://youtrack.jetbrains.com/issue/KTOR-6858
 *
 * Copyright 2014-2024 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package be.alpago.website.libs.ktor.plugins.webjars

class WebjarsConfig {
    var path: String = "/webjars/"
        set(value) {
            field = buildString(value.length + 2) {
                if (!value.startsWith('/')) {
                    append('/')
                }
                append(value)
                if (!endsWith('/')) {
                    append('/')
                }
            }
        }
}
