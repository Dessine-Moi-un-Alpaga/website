import be.alpago.LogbackConfigSerializationGradlePlugin
import com.github.psxpaul.task.ExecFork
import java.util.Locale

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.exec.fork)
    alias(libs.plugins.graalvm.plugin)
    alias(libs.plugins.i18n4k)
    id("jacoco")
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

apply<LogbackConfigSerializationGradlePlugin>()

group = "be.alpago"
version = "latest"

application {
    mainClass.set("be.alpago.website.ApplicationKt")
}

repositories {
    mavenCentral()
}

val webjars by configurations.creating

dependencies {
    implementation(libs.bcrypt)
    implementation(libs.i18n4k)
    implementation(libs.koin)

    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.server)

    runtimeOnly(libs.koin.logger.slf4j)
    runtimeOnly(libs.logback.classic)

    webjars(libs.escape.velocity)
    webjars(libs.photoswipe)
    webjars(libs.toastr)

    testImplementation(platform(libs.junit.bom))

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
}

i18n4k {
    sourceCodeLocales = listOf(
        Locale.FRENCH.language,
        Locale.ENGLISH.language
    )
}

val nativeCompileExtraBuildArgs: String by project

graalvmNative {
    agent {
        defaultMode = "direct"

        modes {
            direct {
                options.add("config-output-dir=src/main/native")
            }
        }
    }
    metadataRepository {
        enabled = true
    }
    binaries {
        named("main") {
            buildArgs.addAll(
                "-Duser.country=BE",
                "-Duser.language=fr",

                "-H:IncludeLocales=fr,en",
                "-H:+ReportExceptionStackTraces",

                "-R:MaxHeapSize=100m",

                "--color=always",

                "--initialize-at-build-time=ch.qos.logback",
                "--initialize-at-build-time=io.ktor,kotlin",
                "--initialize-at-build-time=org.slf4j.LoggerFactory",
                "--initialize-at-build-time=kotlinx.coroutines",

                "--initialize-at-run-time=io.ktor.serialization.kotlinx.json.JsonSupportKt",

                "--install-exit-handlers",
                "--report-unsupported-elements-at-runtime",
            )
            buildArgs.addAll(nativeCompileExtraBuildArgs.split(","))
            configurationFileDirectories.from(file("src/main/native"))
            fallback = false
            imageName = "graalvm-server"
        }
    }
}

tasks.register<Copy>("explodeWebjars") {
    description = "Explodes WebJars to the build resources."
    group = "build"

    webjars.forEach { jar ->
        from(zipTree(jar)) {
            val config = webjars.resolvedConfiguration
            val artifact = config.resolvedArtifacts.find {
                it.file.toString() == jar.absolutePath
            }
            exclude("META-INF/maven/**")
            exclude("META-INF/MANIFEST.MF")
            eachFile {
                val segments = relativePath.segments.drop(5).toTypedArray()
                relativePath = RelativePath(true, artifact!!.name, *segments)
            }
        }
    }
    into("${project.layout.buildDirectory.asFile.get()}/resources/main/static/webjars")
}

tasks.processResources.configure {
    dependsOn(":explodeWebjars")
}

val firebaseLocation: String by project
val firestorePort = 8181
val googleProject: String by project

val firestoreEmulator = tasks.register<ExecFork>("firestoreEmulator") {
    args = mutableListOf(
        "--project",
        googleProject,
        "emulators:start"
    )
    description = "Starts & stops the Firestore emulator."
    executable = firebaseLocation
    group = "application"
    waitForPort = firestorePort
    workingDir = File(projectDir, "firebase-emulator")
}

val home = System.getProperty("user.home")

tasks.named<JavaExec>("run") {
    dependsOn(firestoreEmulator)

    if (!project.hasProperty("agent")) {
        systemProperty("io.ktor.development", "true")
    }
    val configurationDirectory = File(home, ".dmua")
    val secretDirectory = File(configurationDirectory, "secrets")
    val variableDirectory = File(configurationDirectory, "variables")

    val credentials = File(secretDirectory, "CREDENTIALS").readText()
    val devBucket = File(variableDirectory, "DEV_BUCKET").readText()
    val sendGridApiKey = File(secretDirectory, "SEND_GRID_API_KEY").readText()

    environment("DMUA_BASE_ASSET_URL", "https://storage.googleapis.com/${devBucket}")
    environment("DMUA_CREDENTIALS", credentials)
    environment("DMUA_EMAIL_ADDRESS", "contact@dessinemoiunalpaga.com")
    environment("DMUA_ENVIRONMENT", "local")
    environment("DMUA_FIRESTORE_URL", "http://localhost:${firestorePort}")
    environment("DMUA_PROJECT", googleProject)
    environment("DMUA_SEND_GRID_API_KEY", sendGridApiKey)
}

tasks.test.configure {
    dependsOn(firestoreEmulator)
    useJUnitPlatform()
}

tasks.jacocoTestReport.configure {
    reports {
        html.required = false
        xml.required = true
    }
}
