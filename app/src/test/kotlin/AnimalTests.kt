package be.alpago.website

import be.alpago.website.domain.Animal
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.ApplicationTestBuilder
import kotlinx.serialization.json.Json

suspend fun ApplicationTestBuilder.animalTest(id: String) {
    val bannerDescription = "bannerDescription"
    val color = Animal.Color.WHITE
    val dam = Animal.Reference("dam")
    val dateOfBirth = "2023-09-15"
    val name = "name"
    val pageDescription = "pageDescription"
    val prefix = "Dessine-Moi"
    val sex = Animal.Sex.FEMALE
    val sire = Animal.Reference("sire", "sireLink")
    val sold = false
    val subtitle = "subtitle"
    val suffix = "suffix"
    val text = "<p>test</p>"
    val thumbnailDescrtipion = "thumbnailDescription"
    val title = "title"
    val type = Animal.Type.MARE

    val jsonClient = createJsonClient()
    val body = """
                {
                  "id": "$id",
                  "bannerDescription": "$bannerDescription",
                  "color": "$color",
                  "dam": {
                    "name": "$dam"
                  },
                  "dateOfBirth": "$dateOfBirth",
                  "name": "$name",
                  "pageDescription": "$pageDescription",
                  "prefix": "$prefix",
                  "sex": "$sex",
                  "sire": {
                    "name": "${sire.name}",
                    "link": "${sire.link}"
                  },
                  "sold": $sold,
                  "subtitle": "$subtitle",
                  "suffix": "$suffix",
                  "text": "$text",
                  "thumbnailDescription": "$thumbnailDescrtipion",
                  "title": "$title",
                  "type": "$type"
                }
            """.trimIndent()

    var response = jsonClient.put("/api/animals") {
        contentType(ContentType.Application.Json)
        basicAuth(USERNAME, PASSWORD)
        setBody(body)
    }
    response shouldHaveStatus HttpStatusCode.OK

    response = jsonClient.put("/api/fiberAnalyses") {
        contentType(ContentType.Application.Json)
        basicAuth(USERNAME, PASSWORD)
        setBody("""
            {
              "animalId": "$id",
              "averageFiberDiameter": "5µ",
              "coefficientOfVariation": "10%",
              "comfortFactor": "100%",
              "standardDeviation": "1µ",
              "year": 2024
            }
        """.trimIndent())
    }
    response shouldHaveStatus HttpStatusCode.OK

    response = client.get("/animals/${id}.html")
    response shouldHaveStatus HttpStatusCode.OK
}
