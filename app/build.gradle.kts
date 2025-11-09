plugins {
    id("org.jetbrains.kotlin.jvm")

    id("firestore-emulator")
    id("graalvm-native")
    id("i18n")
    id("jacoco")
    id("run")
    id("serialized-logback-config")
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
    implementation(libs.simple.kotlin.mail.client)

    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.server)

    runtimeOnly(libs.logback.classic)

    webjars(libs.escape.velocity)
    webjars(libs.photoswipe)
    webjars(libs.toastr)

    testImplementation(
        platform(libs.junit.bom)
    )

    testImplementation(libs.jsoup)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)
    testImplementation(libs.kotest.assertions.ktor)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotlin.faker)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.server.test.host)
}
