import com.github.psxpaul.task.ExecFork
import org.gradle.kotlin.dsl.provideDelegate
import java.io.File

plugins {
    id("com.github.psxpaul.execfork")
}

val firebaseLocation: String by project
val firestorePort = 8181
val googleProject: String by project

val firestoreEmulator = tasks.register<ExecFork>("firestoreEmulator") {
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
