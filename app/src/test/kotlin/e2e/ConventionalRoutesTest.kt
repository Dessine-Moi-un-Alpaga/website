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
        var response = client.get("/$APPLE_TOUCH_ICON")
        response shouldHaveStatus HttpStatusCode.OK
    }

    @Test
    fun `favicon is available`() = endToEndTest {
        var response = client.get("/$FAVICON")
        response shouldHaveStatus HttpStatusCode.OK
    }

    @Test
    fun `robots_txt is available`() = endToEndTest {
        var response = client.get("/$ROBOTS_TXT")
        response shouldHaveStatus HttpStatusCode.OK
    }
}
