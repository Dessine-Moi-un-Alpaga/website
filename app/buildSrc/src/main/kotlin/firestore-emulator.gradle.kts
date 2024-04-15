import com.github.psxpaul.task.ExecFork
import java.io.ByteArrayOutputStream

plugins {
    id("com.github.psxpaul.execfork")
}

fun String.runCommand(currentWorkingDir: File = file("./")): String {
    val byteOut = ByteArrayOutputStream()
    project.exec {
        workingDir = currentWorkingDir
        commandLine = this@runCommand.split("\\s".toRegex())
        standardOutput = byteOut
    }
    return String(byteOut.toByteArray()).trim()
}

val firebaseLocation = "which firebase".runCommand()
val firestorePort = 8181
val googleProject: String by project

tasks.register<ExecFork>("firestoreEmulator") {
    args = mutableListOf(
        "--project", googleProject,
        "emulators:start"
    )
    description = "Starts & stops the Firestore emulator."
    executable = firebaseLocation
    group = "application"
    waitForPort = firestorePort
    workingDir = File(projectDir, "firebase-emulator")
}
