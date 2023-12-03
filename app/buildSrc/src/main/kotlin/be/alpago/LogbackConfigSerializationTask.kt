package be.alpago

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

open class LogbackConfigSerializationTask : DefaultTask() {

    @InputFile
    open fun getInputFile() = project.file("src/main/logback/logback.xml")

    @OutputFile
    open fun getOutputFile() = File(project.layout.buildDirectory.asFile.get(), "${OUTPUT_DIRECTORY}/logback.scmo")

    @TaskAction
    fun execute() {
        getOutputFile().parentFile.mkdirs()
        val configurator = JoranConfigurator()
        configurator.context = LoggerContext()
        configurator.doConfigure(getInputFile())
        val model = configurator.modelInterpretationContext.topModel
        ObjectOutputStream(FileOutputStream(getOutputFile())).use {
            it.writeObject(model)
        }
    }
}
