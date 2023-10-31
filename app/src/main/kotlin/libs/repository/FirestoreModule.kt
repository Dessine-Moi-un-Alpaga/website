package be.alpago.website.libs.repository

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.ktor.registerCloseable
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import io.ktor.server.application.Application
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun firestoreModule(application: Application) = module {
    single<Firestore> {
        val environment by inject<Environment>()

        FirestoreOptions.getDefaultInstance()
            .toBuilder()
            .setProjectId(environment.project)
            .setCredentials(
                GoogleCredentials.getApplicationDefault()
            )
            .build()
            .service
            .also {
                application.registerCloseable {
                    it.close()
                }
            }
    }
}

fun Application.firestore() {
    koin {
        modules(firestoreModule(this@firestore))
    }
}
