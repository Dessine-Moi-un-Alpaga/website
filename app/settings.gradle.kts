pluginManagement {
    val execForkPluginVersion: String by settings
    val graalvmPluginVersion: String by settings
    val i18n4kVersion: String by settings
    val kotlinVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("com.github.psxpaul.execfork") version execForkPluginVersion
        id("org.graalvm.buildtools.native") version graalvmPluginVersion
        id("de.comahe.i18n4k") version i18n4kVersion
        id("io.ktor.plugin") version ktorVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
}
