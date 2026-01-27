import be.alpago.environmentVariables

afterEvaluate {
    tasks.named<JavaExec>("run") {
        if (!project.hasProperty("agent")) {
            systemProperty("io.ktor.development", "true")
        }

        environmentVariables(project)
    }
}
