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
                "-Duser.country=BE",
                "-Duser.language=fr",

                "-H:IncludeLocales=fr,en",
                "-H:+ReportExceptionStackTraces",

                "-R:MaxHeapSize=100m",

                "--color=always",

                "--initialize-at-build-time=ch.qos.logback",
                "--initialize-at-build-time=io.ktor,kotlin",
                "--initialize-at-build-time=org.slf4j.LoggerFactory",
                "--initialize-at-build-time=kotlinx.coroutines",

                "--initialize-at-run-time=io.ktor.serialization.kotlinx.json.JsonSupportKt",

                "--install-exit-handlers",
                "--report-unsupported-elements-at-runtime",
            )
            buildArgs.addAll(nativeCompileExtraBuildArgs.split(","))
            configurationFileDirectories.from(file("src/main/native"))
            fallback = false
            imageName = "graalvm-server"
        }
    }
}
