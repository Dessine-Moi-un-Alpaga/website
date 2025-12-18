import com.github.psxpaul.task.ExecFork

plugins {
    id("com.github.psxpaul.execfork")
}


val firebaseLocation: String by project
val firestorePort = project.property("firestorePort") as String
val googleProject: String by project

tasks.register<ExecFork>("firestoreEmulator") {
    args = mutableListOf(
        "--project", googleProject,
        "emulators:start"
    )
    description = "Starts & stops the Firestore emulator."
    executable = firebaseLocation
    group = "application"
    waitForPort = firestorePort.toInt()
    workingDir = File(projectDir, "firebase-emulator")
}
