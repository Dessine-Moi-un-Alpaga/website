package be.alpago

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import javax.inject.Inject

const val OUTPUT_DIRECTORY = "generated/resources/serialized-logback-config"

abstract class LogbackConfigSerializationTask : DefaultTask() {

    @get:Inject
    abstract val projectLayout: ProjectLayout

    @get:InputFile
    val inputFile: RegularFile = projectLayout.projectDirectory.file("src/main/logback/logback.xml")

    @get:OutputFile
    val outputFile: Provider<RegularFile> = projectLayout.buildDirectory.file("$OUTPUT_DIRECTORY/logback.scmo")

    @TaskAction
    fun execute() {
        val outputFile = outputFile.get().asFile
        outputFile.parentFile.mkdirs()
        val configurator = JoranConfigurator()
        configurator.context = LoggerContext()
        configurator.doConfigure(inputFile.asFile)
        val model = configurator.modelInterpretationContext.topModel
        ObjectOutputStream(FileOutputStream(outputFile)).use {
            it.writeObject(model)
        }
    }
}
