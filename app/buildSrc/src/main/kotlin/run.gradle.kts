import be.alpago.environmentVariables

afterEvaluate {
    tasks.named<JavaExec>("run") {
        dependsOn(":firestoreEmulator")

        if (!project.hasProperty("agent")) {
            systemProperty("io.ktor.development", "true")
        }

        environmentVariables(project)
    }
}
