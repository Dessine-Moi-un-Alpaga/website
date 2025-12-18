package be.alpago

import org.gradle.api.Project
import org.gradle.process.ProcessForkOptions

fun ProcessForkOptions.environmentVariables(project: Project) {
    val credentials = project.property("credentials")
    val devBucket = project.property("devBucket")
    val firestorePort = project.property("firestorePort")
    val googleProject = project.property("googleProject")
    val smtpServerAddress = project.property("smtpServerAddress")
    val smtpServerPassword = project.property("smtpServerPassword")
    val smtpServerPort = project.property("smtpServerPort")
    val smtpServerUsername = project.property("smtpServerUsername")

    environment("DMUA_BASE_ASSET_URL", "https://storage.googleapis.com/${devBucket}")
    environment("DMUA_CREDENTIALS", credentials)
    environment("DMUA_EMAIL_ADDRESS", "contact@dessinemoiunalpaga.com")
    environment("DMUA_ENVIRONMENT", "local")
    environment("DMUA_FIRESTORE_URL", "http://localhost:${firestorePort}")
    environment("DMUA_PROJECT", googleProject)
    environment("DMUA_SMTP_SERVER_ADDRESS", smtpServerAddress)
    environment("DMUA_SMTP_SERVER_PASSWORD", smtpServerPassword)
    environment("DMUA_SMTP_SERVER_PORT", smtpServerPort)
    environment("DMUA_SMTP_SERVER_USERNAME", smtpServerUsername)
    environment("DMUA_TEST", true)
}
