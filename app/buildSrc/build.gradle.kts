plugins {
    kotlin("jvm") version "1.9.21"
}

repositories {
    mavenCentral()
}

val logbackVersion = "1.4.14"

dependencies {
    compileOnly(gradleApi())
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}
