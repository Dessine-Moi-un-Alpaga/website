plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.i18n4k.gradle.plugin)
    implementation(libs.native.gradle.plugin)
}
