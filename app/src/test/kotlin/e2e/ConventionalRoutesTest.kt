package com.dessinemoiunalpaga.website.e2e

import com.dessinemoiunalpaga.website.interfaces.ktor.routes.APPLE_TOUCH_ICON
import com.dessinemoiunalpaga.website.interfaces.ktor.routes.FAVICON
import com.dessinemoiunalpaga.website.interfaces.ktor.routes.ROBOTS_TXT
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.junit.jupiter.api.Test

class ConventionalRoutesTest {

    @Test
    fun `apple touch icon is available`() = endToEndTest {
        client.get("/$APPLE_TOUCH_ICON") shouldHaveStatus HttpStatusCode.OK
    }

    @Test
    fun `favicon is available`() = endToEndTest {
        client.get("/$FAVICON") shouldHaveStatus HttpStatusCode.OK
    }

    @Test
    fun `robots_txt is available`() = endToEndTest {
        client.get("/$ROBOTS_TXT") shouldHaveStatus HttpStatusCode.OK
    }
}
