plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())

    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.exec.fork.plugin)
    implementation(libs.i18n4k.gradle.plugin)
    implementation(libs.native.gradle.plugin)

    implementation(libs.logback.classic)
}
