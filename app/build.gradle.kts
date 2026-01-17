import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)

    id("firestore-emulator")
    id("graalvm-native")
    id("i18n")
    id("run")
    id("test")
    id("webjars")

    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "be.alpago"

application {
    mainClass.set("be.alpago.website.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bcrypt)
    implementation(libs.i18n4k)
    implementation(libs.jakarta.mail.api)
    implementation(libs.jul.to.slf4j)

    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.server)

    runtimeOnly(libs.angus.smtp)
    runtimeOnly(libs.slf4j.simple)

    webjars(libs.escape.velocity)
    webjars(libs.photoswipe)
    webjars(libs.toastr)

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
        jvmTarget.set(JvmTarget.JVM_25)
    }
}
