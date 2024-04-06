package be.alpago

import org.gradle.api.Project
import org.gradle.process.ProcessForkOptions

fun ProcessForkOptions.environmentVariables(project: Project) {
    val credentials = project.property("credentials")
    val devBucket = project.property("devBucket")
    val firestorePort = project.property("firestorePort")
    val googleProject = project.property("googleProject")
    val sendGridApiKey = project.property("sendGridApiKey")

    environment("DMUA_BASE_ASSET_URL", "https://storage.googleapis.com/${devBucket}")
    environment("DMUA_CREDENTIALS", credentials)
    environment("DMUA_EMAIL_ADDRESS", "contact@dessinemoiunalpaga.com")
    environment("DMUA_ENVIRONMENT", "local")
    environment("DMUA_FIRESTORE_URL", "http://localhost:${firestorePort}")
    environment("DMUA_PROJECT", googleProject)
    environment("DMUA_SEND_GRID_API_KEY", sendGridApiKey)
    environment("DMUA_TEST", true)
}
