package be.alpago.website.modules.animal

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import com.google.cloud.firestore.Firestore
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

private fun animalModule() = module {
    single<CrudRepository<Animal>>(
        named(AnimalRepositories.animal)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreAnimalRepository(
                db = db,
                environment = environment.name
            )
        )
    }

    single<CrudRepository<FiberAnalysis>>(
        named(AnimalRepositories.fiberAnalyses)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreFiberAnalysisRepository(
                db = db,
                environment = environment.name
            )
        )
    }
}

fun Application.animals() {
    koin {
        modules(
            animalModule()
        )
    }

    animalRoutes()
}
