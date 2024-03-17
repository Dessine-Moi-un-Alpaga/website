package be.alpago.website

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.delete
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder

const val USERNAME = "test"
const val PASSWORD = "**SECRET**"
const val CREDENTIALS = "$USERNAME:\$2y\$12\$MEq8DuMADQU85PFGw844zuAVbEXOtyC1oTorFISvrsPIoQ9Rn92qy"

fun ApplicationTestBuilder.createJsonClient() = createClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun ApplicationTestBuilder.delete(
    url: String,
) {
    val jsonClient = createJsonClient()
    val response = jsonClient.delete(url) {
        basicAuth(USERNAME, PASSWORD)
    }
    response shouldHaveStatus HttpStatusCode.OK
}
