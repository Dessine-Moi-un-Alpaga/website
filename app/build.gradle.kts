import be.alpago.LogbackConfigSerializationGradlePlugin
import com.github.psxpaul.task.ExecFork

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.exec.fork)
    alias(libs.plugins.graalvm.plugin)
    alias(libs.plugins.i18n4k)
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
    sourceCodeLocales = listOf("fr", "en")
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
        enabled.set(true)
    }
    binaries {
        named("main") {
            fallback.set(false)

            buildArgs.add("-R:MaxHeapSize=100m")

            buildArgs.add("--initialize-at-build-time=ch.qos.logback")
            buildArgs.add("--initialize-at-build-time=io.ktor,kotlin")
            buildArgs.add("--initialize-at-build-time=org.slf4j.LoggerFactory")
            buildArgs.add("--initialize-at-build-time=kotlinx.coroutines")

            buildArgs.add("--initialize-at-run-time=io.ktor.serialization.kotlinx.json.JsonSupportKt")

            buildArgs.add("-H:IncludeLocales=fr,en")
            buildArgs.add("-H:+InstallExitHandlers")
            buildArgs.add("-H:+ReportUnsupportedElementsAtRuntime")
            buildArgs.add("-H:+ReportExceptionStackTraces")

            buildArgs.add("-Duser.country=BE")
            buildArgs.add("-Duser.language=fr")

            buildArgs.add("--color=always")

            buildArgs.addAll(nativeCompileExtraBuildArgs.split(","))

            configurationFileDirectories.from(file("src/main/native"))

            imageName.set("graalvm-server")
        }
    }
}

tasks.register<Copy>("explodeWebjars") {
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

val home = System.getProperty("user.home")

val firestorePort = 8181

val firestoreEmulator = tasks.register<ExecFork>("firestoreEmulator") {
    executable = "firebase"
    args = mutableListOf("emulators:start")
    workingDir = File(home, ".firestore")
    waitForPort = firestorePort
}

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
    val googleProject = File(variableDirectory, "GOOGLE_PROJECT").readText()
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
