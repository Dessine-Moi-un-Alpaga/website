package be.alpago.website.interfaces.ktor

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.ports.AggregateRootNotFound
import be.alpago.website.libs.domain.ports.Repository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext

typealias RepositoryProvider<T> = (PipelineContext<Unit, ApplicationCall>) -> Repository<T>

const val ID = "id"

inline fun <reified T : AggregateRoot> Route.findAllRoute(
    path: String,
    crossinline repositoryProviderFor: RepositoryProvider<T>
) {
    get(path) {
        val repository = repositoryProviderFor(this)
        call.respond(
            repository.findAll()
        )
    }
}

inline fun <reified T : AggregateRoot> Route.getRoute(
    path: String,
    crossinline repositoryProviderFor: RepositoryProvider<T>
) {
    get("${path}/{$ID}") {
        val id = call.parameters[ID]

        if (id == null) {
            call.response.status(HttpStatusCode.BadRequest)
        } else {
            val repository = repositoryProviderFor(this)

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
    crossinline repositoryProviderFor: RepositoryProvider<T>
) {
    put(path) {
        val aggregateRoot = call.receive<T>()
        val repository = repositoryProviderFor(this)
        repository.create(aggregateRoot)
        call.response.status(HttpStatusCode.OK)
    }
}

inline fun <reified T : AggregateRoot> Route.deleteAllRoute(
    path: String,
    crossinline repositoryProviderFor: RepositoryProvider<T>
) {
    delete(path) {
        val repository = repositoryProviderFor(this)
        repository.deleteAll()
        call.response.status(HttpStatusCode.OK)
    }
}

inline fun <reified T : AggregateRoot> Route.deleteRoute(
    path: String,
    crossinline repositoryProviderFor: RepositoryProvider<T>
) {
    delete("${path}/{$ID}") {
        val id = call.parameters[ID]

        if (id == null) {
            call.response.status(HttpStatusCode.BadRequest)
        } else {
            val repository = repositoryProviderFor(this)

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
    crossinline repositoryProvider: RepositoryProvider<T>
) {
    routing {
        authenticate {
            findAllRoute(path, repositoryProvider)
            getRoute(path, repositoryProvider)
            putRoute(path, repositoryProvider)
            deleteAllRoute(path, repositoryProvider)
            deleteRoute(path, repositoryProvider)
        }
    }
}

inline fun <reified T : AggregateRoot> Application.managementRoutes(
    path: String,
    repository: Repository<T>
) {
    managementRoutes(path) { repository }
}
