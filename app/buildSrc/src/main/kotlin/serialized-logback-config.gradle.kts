import be.alpago.LogbackConfigSerializationTask
import be.alpago.OUTPUT_DIRECTORY

val taskName = "serializeLogbackConfig"

tasks.register<LogbackConfigSerializationTask>(taskName) {
    description = "Serialize Logback configuration"
    group = "serialized-logback-config"
}

afterEvaluate {
    tasks.withType(ProcessResources::class.java)
        .configureEach {
            dependsOn(taskName)
        }
}

val outputDirectory = File(layout.buildDirectory.asFile.get(), OUTPUT_DIRECTORY)
outputDirectory.mkdirs()
val sourceSets = properties["sourceSets"] as SourceSetContainer
val resources = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).resources
resources.srcDirs(outputDirectory)
