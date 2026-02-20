package be.alpago.website.libs.ktor.routes

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.ports.persistence.AggregateRootNotFound
import be.alpago.website.libs.domain.ports.persistence.Repository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing

/**
 * @suppress
 */
const val ID = "id"

/**
 * Registers a route using the given context path that lists all the [AggregateRoot]s using the given [Repository].
 */
inline fun <reified T : AggregateRoot> Route.findAllRoute(
    path: String,
    repository: Repository<T>,
) {
    get(path) {
        call.respond(
            repository.findAll()
        )
    }
}

inline fun <reified T : AggregateRoot> Route.getRoute(
    path: String,
    repository: Repository<T>,
) {
    get("${path}/{$ID}") {
        val id = call.parameters[ID]

        if (id == null) {
            call.response.status(HttpStatusCode.BadRequest)
        } else {
            try {
                val aggregateRoot = repository.get(id)
                call.respond(aggregateRoot)
            } catch (e: AggregateRootNotFound) {
                call.response.status(HttpStatusCode.NotFound)
            }
        }
    }
}

inline fun <reified T : AggregateRoot> Route.putRoute(
    path: String,
    repository: Repository<T>,
) {
    put(path) {
        val aggregateRoot = call.receive<T>()
        repository.save(aggregateRoot)
        call.response.status(HttpStatusCode.OK)
    }
}

inline fun <reified T : AggregateRoot> Route.deleteAllRoute(
    path: String,
    repository: Repository<T>,
) {
    delete(path) {
        repository.deleteAll()
        call.response.status(HttpStatusCode.OK)
    }
}

inline fun <reified T : AggregateRoot> Route.deleteRoute(
    path: String,
    repository: Repository<T>,
) {
    delete("${path}/{$ID}") {
        val id = call.parameters[ID]

        if (id == null) {
            call.response.status(HttpStatusCode.BadRequest)
        } else {
            try {
                repository.delete(id)
                call.response.status(HttpStatusCode.OK)
            } catch (e: AggregateRootNotFound) {
                call.response.status(HttpStatusCode.NotFound)
            }
        }
    }
}

inline fun <reified T : AggregateRoot> Application.managementRoutes(
    path: String,
    repository: Repository<T>
) {
    routing {
        authenticate {
            findAllRoute(path, repository)
            getRoute(path, repository)
            putRoute(path, repository)
            deleteAllRoute(path, repository)
            deleteRoute(path, repository)
        }
    }
}
