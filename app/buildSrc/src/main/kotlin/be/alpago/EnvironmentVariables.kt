package be.alpago

import org.gradle.api.Project
import org.gradle.process.ProcessForkOptions

fun ProcessForkOptions.environmentVariables(project: Project) {
    project.property("bucket")?.let {
        environment("DMUA_BASE_ASSET_URL",  "https://storage.googleapis.com/${it}")
    }

    project.property("credentials")?.let {
        environment("DMUA_CREDENTIALS", it)
    }

    environment("DMUA_EMAIL_ADDRESS", "contact@dessinemoiunalpaga.com")
    environment("DMUA_ENVIRONMENT", "local")

    project.property("firestorePort")?.let {
        environment("DMUA_FIRESTORE_URL","http://localhost:${it}")
    }

    project.property("googleProject")?.let {
        environment("DMUA_PROJECT", it)
    }

    project.property("sendGridApiKey")?.let {
        environment("DMUA_SEND_GRID_API_KEY", it)
    }

    environment("DMUA_TEST", true)
}
