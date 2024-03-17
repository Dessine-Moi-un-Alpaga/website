import be.alpago.LogbackConfigSerializationTask
import be.alpago.OUTPUT_DIRECTORY
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.language.jvm.tasks.ProcessResources
import java.io.File

val taskName = "serializeLogbackConfig"

tasks.create(
    taskName,
    LogbackConfigSerializationTask::class.java
) {
    description = "Serialize Logback configuration"
    group = "serialized-logback-config"
}

afterEvaluate {
    tasks.withType(ProcessResources::class.java)
        .configureEach {
            dependsOn(taskName)
        }
}

val outputDirectory = File(project.layout.buildDirectory.asFile.get(), OUTPUT_DIRECTORY)
outputDirectory.mkdirs()
val sourceSets = project.properties["sourceSets"] as SourceSetContainer
val resources = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).resources
resources.srcDirs(outputDirectory)
