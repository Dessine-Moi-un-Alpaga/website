package be.alpago.website.libs.ktor

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.AggregateRootNotFound
import be.alpago.website.libs.repository.CrudRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext

inline fun <reified T : AggregateRoot> Application.managementRoutes(path: String, crossinline repositoryProvider: (PipelineContext<Unit, ApplicationCall>) -> CrudRepository<T>) {
    routing {
        authenticate {
            get(path) {
                val repository = repositoryProvider(this)
                call.respond(
                    repository.findAll()
                )
            }

            get("${path}/{id}") {
                val id = call.parameters["id"]

                if (id == null) {
                    call.response.status(HttpStatusCode.BadRequest)
                } else {
                    val repository = repositoryProvider(this)
                    try {
                        val aggregateRoot = repository.get(id)
                        call.respond(aggregateRoot)
                    } catch (e: AggregateRootNotFound) {
                        call.response.status(HttpStatusCode.NotFound)
                    }
                }
            }

            put(path) {
                val aggregateRoot = call.receive<T>()
                val repository = repositoryProvider(this)
                repository.createOrUpdate(aggregateRoot)
                call.response.status(HttpStatusCode.OK)
            }

            delete(path) {
                val repository = repositoryProvider(this)
                repository.deleteAll()
                call.response.status(HttpStatusCode.OK)
            }

            delete("${path}/{id}") {
                val id = call.parameters["id"]!!
                val repository = repositoryProvider(this)

                try {
                    repository.delete(id)
                    call.response.status(HttpStatusCode.OK)
                } catch (e: AggregateRootNotFound) {
                    call.response.status(HttpStatusCode.NotFound)
                }
            }
        }
    }
}

inline fun <reified T : AggregateRoot> Application.managementRoutes(path: String, repository: CrudRepository<T>) {
    managementRoutes(path) { repository }
}