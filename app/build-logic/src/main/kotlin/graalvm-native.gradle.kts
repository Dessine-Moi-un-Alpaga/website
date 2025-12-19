plugins {
    id("org.graalvm.buildtools.native")
}

graalvmNative {
    agent {
        defaultMode = "direct"

        modes {
            direct {
                options.add("config-output-dir=src/main/resources/META-INF/native-image/be.alpago/website")
            }
        }
    }
    metadataRepository {
        enabled = true
    }
}
