plugins {
    id("org.graalvm.buildtools.native")
}

val nativeCompileExtraBuildArgs: String by project

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
        enabled = true
    }
    binaries {
        named("main") {
            buildArgs.addAll(
                "-Dorg.slf4j.simpleLogger.logFile=System.out",
                "-Dorg.slf4j.simpleLogger.cacheOutputStream=true",
                "-Dorg.slf4j.simpleLogger.showDateTime=true",
                "-Dorg.slf4j.simpleLogger.dateTimeFormat=HH:mm:ss.SSS",
                "-Duser.country=BE",
                "-Duser.language=fr",

                "-H:IncludeLocales=fr,en",
                "-H:+ReportExceptionStackTraces",

                "-R:MaxHeapSize=100m",

                "--color=always",

                "--initialize-at-build-time=io.ktor,kotlin",
                "--initialize-at-build-time=kotlinx.coroutines",
                "--initialize-at-build-time=kotlinx.io.Buffer",
                "--initialize-at-build-time=kotlinx.io.Segment",
                "--initialize-at-build-time=kotlinx.io.Segment\$Companion",
                "--initialize-at-build-time=kotlinx.io.bytestring.ByteString",
                "--initialize-at-build-time=kotlinx.io.bytestring.ByteString\$Companion",
                "--initialize-at-build-time=org.slf4j.LoggerFactory",
                "--initialize-at-build-time=org.slf4j.helpers.BasicMDCAdapter",
                "--initialize-at-build-time=org.slf4j.helpers.NOPMDCAdapter",
                "--initialize-at-build-time=org.slf4j.helpers.NOP_FallbackServiceProvider",
                "--initialize-at-build-time=org.slf4j.helpers.NOPLoggerFactory",
                "--initialize-at-build-time=org.slf4j.helpers.SubstituteLoggerFactory",
                "--initialize-at-build-time=org.slf4j.helpers.SubstituteServiceProvider",
                "--initialize-at-build-time=org.slf4j.simple.OutputChoice",
                "--initialize-at-build-time=org.slf4j.simple.OutputChoice\$OutputChoiceType",
                "--initialize-at-build-time=org.slf4j.simple.SimpleLogger",
                "--initialize-at-build-time=org.slf4j.simple.SimpleLoggerConfiguration",
                "--initialize-at-build-time=org.slf4j.simple.SimpleServiceProvider",

                "--initialize-at-run-time=io.ktor.serialization.kotlinx.json.JsonSupportKt",

                "--install-exit-handlers",
            )
            buildArgs.addAll(nativeCompileExtraBuildArgs.split(","))
            configurationFileDirectories.from(file("src/main/native"))
            fallback = false
            imageName = "graalvm-server"
        }
    }
}
