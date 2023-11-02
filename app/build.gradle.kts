import org.gradle.internal.os.OperatingSystem

plugins {
    kotlin("jvm") version "1.9.20"
    id("de.comahe.i18n4k") version "0.6.2"
    kotlin("plugin.serialization") version "1.9.20"
    id("io.ktor.plugin") version "2.3.5"
    id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "be.alpago"
version = "0.1.0-SNAPSHOT"

application {
    mainClass.set("be.alpago.website.ApplicationKt")
}

repositories {
    mavenCentral()
}

val webjars by configurations.creating

val bcryptVersion = "0.10.2"
val escapeVelocityVersion = "1.0.0-1"
val googleCloudLibrariesBomVersion = "26.26.0"
val i18n4kVersion = "0.6.2"
val koinVersion = "3.5.1"
val kotlinCssVersion = "1.0.0-pre.636"
val logbackVersion = "1.4.11"
val photoswipeVersion = "5.3.7"
val sendgridVersion = "4.9.3"
val toastrVersion = "2.1.2"

dependencies {
    implementation(platform("com.google.cloud:libraries-bom:$googleCloudLibrariesBomVersion"))

    implementation("at.favre.lib:bcrypt:$bcryptVersion")
    implementation("com.google.cloud:google-cloud-firestore")
    implementation("de.comahe.i18n4k:i18n4k-core-jvm:$i18n4kVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:$kotlinCssVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-caching-headers")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("io.ktor:ktor-server-request-validation")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("com.sendgrid:sendgrid-java:$sendgridVersion")

    runtimeOnly("io.insert-koin:koin-logger-slf4j:$koinVersion")
    runtimeOnly("ch.qos.logback:logback-classic:$logbackVersion")

    webjars("org.webjars:escape-velocity:$escapeVelocityVersion")
    webjars("org.webjars.npm:photoswipe:$photoswipeVersion")
    webjars("org.webjars:toastr:$toastrVersion")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

i18n4k {
    sourceCodeLocales = listOf("fr", "en")
}

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
            verbose.set(true)

            buildArgs.add("--initialize-at-build-time=ch.qos.logback")
            buildArgs.add("--initialize-at-build-time=io.ktor,kotlin")
            buildArgs.add("--initialize-at-build-time=org.slf4j.LoggerFactory")

            buildArgs.add("--initialize-at-run-time=io.ktor.serialization.kotlinx.json.JsonSupportKt")

            buildArgs.add("-H:IncludeLocales=fr,en")
            buildArgs.add("-H:+InstallExitHandlers")
            buildArgs.add("-H:+ReportUnsupportedElementsAtRuntime")
            buildArgs.add("-H:+ReportExceptionStackTraces")

            buildArgs.add("-Duser.country=BE")
            buildArgs.add("-Duser.language=fr")

            if (OperatingSystem.current().familyName == OperatingSystemFamily.LINUX) {
                buildArgs.add("--static")
                buildArgs.add("--libc=musl")
            }

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
    into("${project.buildDir}/resources/main/static/webjars")
}

tasks.processResources.configure {
    dependsOn(":explodeWebjars", ":generateI18n4kFiles")
}

tasks.named<JavaExec>("run") {
    if (!project.hasProperty("agent")) {
        systemProperty("io.ktor.development", "true")
    }

    val home = System.getProperty("user.home")
    val configurationDirectory = File(home, ".dmua")
    val secretDirectory = File(configurationDirectory, "secrets")
    val variableDirectory = File(configurationDirectory, "variables")

    val credentials = File(secretDirectory, "CREDENTIALS").readText()
    val devBucket = File(variableDirectory, "DEV_BUCKET").readText()
    val googleProject = File(variableDirectory, "GOOGLE_PROJECT").readText()
    val sendGridApiKey = File(secretDirectory, "SEND_GRID_API_KEY").readText()

    environment("DMUA_BASE_ASSET_URL", "https://storage.googleapis.com/${devBucket}")
    environment("DMUA_CREDENTIALS", credentials)
    environment("DMUA_ENVIRONMENT", "local")
    environment("DMUA_PROJECT", googleProject)
    environment("DMUA_SEND_GRID_API_KEY", sendGridApiKey)
}
