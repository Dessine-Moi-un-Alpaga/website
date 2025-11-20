package be.alpago.website.interfaces.ktor.routes

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private const val NEW_ANIMAL_ROUTE_PATTERN = "/animals/%s"
private const val PATH_VARIABLE = "path"

fun Application.legacyRoutes() {
    val properties: TemplateProperties by dependencies

    val movedResources = mapOf(
        "/animals/females/{${PATH_VARIABLE}}" to NEW_ANIMAL_ROUTE_PATTERN,
        "/animals/for-sale/{${PATH_VARIABLE}}" to NEW_ANIMAL_ROUTE_PATTERN,
        "/animals/geldings/{${PATH_VARIABLE}}" to NEW_ANIMAL_ROUTE_PATTERN,
        "/animals/sold/{${PATH_VARIABLE}}" to NEW_ANIMAL_ROUTE_PATTERN,
        "/factsheets/{${PATH_VARIABLE}}" to "${properties.baseAssetUrl}/factsheets/%s",
        "/images/animals/{${PATH_VARIABLE}}" to "${properties.baseAssetUrl}/images/animals/%s",
        "/images/gallery/{${PATH_VARIABLE}}" to "${properties.baseAssetUrl}/images/gallery/%s",
        "/images/gallery/thumbnails/{${PATH_VARIABLE}}" to "${properties.baseAssetUrl}/images/gallery/thumbnails/%s",
    )

    routing {
        movedResources.forEach { movedResource ->
            val newRoutePattern = movedResource.value
            val oldRoutePattern = movedResource.key

            get(oldRoutePattern) {
                val pathParameter = call.parameters[PATH_VARIABLE]
                call.respondRedirect(
                    url = String.format(newRoutePattern, pathParameter),
                    permanent = true,
                )
            }
        }
    }
}
