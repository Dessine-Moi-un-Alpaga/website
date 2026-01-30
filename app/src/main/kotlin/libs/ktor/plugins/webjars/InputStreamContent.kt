/*
 * Temporary workaround for https://youtrack.jetbrains.com/issue/KTOR-6858
 *
 * Copyright 2014-2024 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package be.alpago.website.libs.ktor.plugins.webjars

import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.util.cio.KtorDefaultPool
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.jvm.javaio.toByteReadChannel
import java.io.InputStream

internal class InputStreamContent(
    private val input: InputStream,
    override val contentType: ContentType
) : OutgoingContent.ReadChannelContent() {

    override fun readFrom(): ByteReadChannel = input.toByteReadChannel(pool = KtorDefaultPool)
}
