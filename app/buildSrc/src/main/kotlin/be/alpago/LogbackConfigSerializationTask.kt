package be.alpago

import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.classic.LoggerContext
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

open class LogbackConfigSerializationTask : DefaultTask() {

    @InputFile
    open fun getInputFile() = project.file("src/main/logback/logback.xml")

    @OutputFile
    open fun getOutputFile() = File(project.buildDir, "${OUTPUT_DIRECTORY}/logback.scmo")

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