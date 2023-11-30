package be.alpago

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.language.jvm.tasks.ProcessResources
import java.io.File

const val OUTPUT_DIRECTORY = "generated/resources/serialized-logback-config"

private const val TASK_NAME = "generateSerializedLogbackConfig"

class LogbackConfigSerializationGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        defineTasks(project)
        project.afterEvaluate { addTaskDependencies(project) }
        addSerializedLogbackConfigToResources(project)
    }

    private fun defineTasks(project: Project) {
        project.tasks.create(
            TASK_NAME,
            LogbackConfigSerializationTask::class.java
        ) {
            it.description = "Serialize Logback configuration"
            it.group = "serialized-logback-config"
        }
    }

    private fun addTaskDependencies(project: Project) {
        project.tasks.withType(ProcessResources::class.java)
            .configureEach { it.dependsOn(TASK_NAME) }
    }

    private fun addSerializedLogbackConfigToResources(project: Project) {
        val outputDirectory = File(project.buildDir, OUTPUT_DIRECTORY)
        outputDirectory.mkdirs()
        val sourceSets = project.properties["sourceSets"] as SourceSetContainer
        val resources = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).resources
        resources.srcDirs(outputDirectory)
    }
}
