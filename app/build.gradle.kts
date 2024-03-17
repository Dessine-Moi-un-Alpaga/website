import be.alpago.environmentVariables

plugins {
    id("firestore-emulator")
    id("i18n")
    id("jacoco")
    id("graalvm-native")
    id("run")
    id("serialized-logback-config")
    id("test")
    id("webjars")

    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "be.alpago"
version = "latest"

application {
    mainClass.set("be.alpago.website.ApplicationKt")
}

repositories {
    mavenCentral()
}

val webjars by configurations.named("webjars")

dependencies {
    implementation(libs.bcrypt)
    implementation(libs.i18n4k)

    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.server)

    runtimeOnly(libs.logback.classic)

    webjars(libs.escape.velocity)
    webjars(libs.photoswipe)
    webjars(libs.toastr)

    testImplementation(platform(libs.junit.bom))

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)
    testImplementation(libs.jsoup)
    testImplementation(libs.kotest.assertions.ktor)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.server.test.host)
}
