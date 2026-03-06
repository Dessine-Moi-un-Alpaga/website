package be.alpago.website.e2e

import be.alpago.website.interfaces.ktor.routes.APPLE_TOUCH_ICON
import be.alpago.website.interfaces.ktor.routes.FAVICON
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
