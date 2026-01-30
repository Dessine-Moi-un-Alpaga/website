import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)

    id("graalvm-native")
    id("i18n")
    id("run")
    id("test")

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
        jvmTarget.set(JvmTarget.JVM_25)
    }
}

tasks.register("ci") {
    group = "build"
    description = "Executes continuous integration build tasks."
    dependsOn(tasks.test, tasks.jacocoTestReport, tasks.shadowJar)
}
