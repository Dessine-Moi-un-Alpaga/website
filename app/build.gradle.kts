import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.time.LocalDate

plugins {
    alias(libs.plugins.kotlin)

    alias(libs.plugins.dokka)
    alias(libs.plugins.i18n4k)
    alias(libs.plugins.graalvm.plugin)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)

    jacoco
}

group = "be.alpago"

application {
    mainClass.set("be.alpago.website.ApplicationKt")
}

repositories {
    mavenCentral()
}

configurations {
    "runtimeClasspath" {
        resolutionStrategy.force(libs.jquery)
    }
}

dependencies {
    implementation(libs.bcrypt)
    implementation(libs.i18n4k)
    implementation(libs.jakarta.mail.api)
    implementation(libs.jul.to.slf4j)
    implementation(libs.webjars.locator.lite)

    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.server)

    runtimeOnly(libs.angus.smtp)
    runtimeOnly(libs.slf4j.simple)

    runtimeOnly(libs.escape.velocity)
    runtimeOnly(libs.photoswipe)
    runtimeOnly(libs.toastr)

    testImplementation(
        platform(libs.junit.bom)
    )

    testImplementation(libs.jsoup)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlin.faker)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.mockk)

    testImplementation(libs.bundles.kotest)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(project.property("javaVersion").toString()))
    }
}

tasks.register("ci") {
    group = "build"
    description = "Executes continuous integration build tasks."
    dependsOn(tasks.jacocoTestReport, tasks.shadowJar, tasks.dokkaGenerate)
}

fun ProcessForkOptions.environmentVariables(project: Project) {
    val credentials = project.property("credentials")
    val bucket = project.property("bucket")
    val firestorePort = project.property("firestorePort")
    val googleProject = project.property("googleProject")
    val smtpServerAddress = project.property("smtpServerAddress")
    val smtpServerPassword = project.property("smtpServerPassword")
    val smtpServerPort = project.property("smtpServerPort")
    val smtpServerUsername = project.property("smtpServerUsername")

    environment("DMUA_BASE_ASSET_URL", "https://storage.googleapis.com/${bucket}")
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

graalvmNative {
    agent {
        defaultMode = "direct"

        modes {
            direct {
                options.add("config-output-dir=src/main/resources/META-INF/native-image/be.alpago/website")
            }
        }
    }
    metadataRepository {
        enabled = true
    }
}

tasks.test {
    useJUnitPlatform()
    environmentVariables(project)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        html.required = false
        xml.required = true
    }
}

tasks.run {
    if (!project.hasProperty("agent")) {
        systemProperty("io.ktor.development", "true")
    }

    environmentVariables(project)
}

dokka {
    dokkaSourceSets.main {
        includes.from("src/main/kotlin/package-info.md")
        jdkVersion.set(project.property("javaVersion").toString().toInt())

        val suppressedPackages = listOf(
            "be.alpago.website",
            "be.alpago.website.i18n",
            "be.alpago.website.interfaces.kotlinx.html.body(.*)",
            "be.alpago.website.interfaces.kotlinx.html.footer(.*)",
            "be.alpago.website.interfaces.kotlinx.html.head(.*)",
            "be.alpago.website.interfaces.kotlinx.html.header(.*)",
            "be.alpago.website.interfaces.kotlinx.html.style(.*)",
        )

        for (suppressedPackage in suppressedPackages) {
            perPackageOption {
                matchingRegex.set(suppressedPackage)
                suppress.set(true)
            }
        }

        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://github.com/Dessine-Moi-un-Alpaga/website/tree/main/app/src/main/kotlin")
        }

        val externalApiDocs = mapOf(
            "kotlinx-serialization" to Pair("https://kotlinlang.org/api/kotlinx.serialization", "https://kotlinlang.org/api/kotlinx.serialization/package-list"),
            "ktor" to Pair("https://api.ktor.io", "https://api.ktor.io/package-list"),
        )

        for (externalApiDoc in externalApiDocs) {
            externalDocumentationLinks.register(externalApiDoc.key) {
                url(externalApiDoc.value.first)
                packageListUrl(externalApiDoc.value.second)
            }
        }
    }

    pluginsConfiguration.html {
        footerMessage.set("© ${LocalDate.now().year} Alpago")
    }
}

tasks.dokkaGeneratePublicationHtml.configure {
    dependsOn(tasks.generateI18n4kFiles)
}
