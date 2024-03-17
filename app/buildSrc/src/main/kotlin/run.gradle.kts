import be.alpago.environmentVariables
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.named

afterEvaluate {
    tasks.named<JavaExec>("run") {
        dependsOn(":firestoreEmulator")

        if (!project.hasProperty("agent")) {
            systemProperty("io.ktor.development", "true")
        }

        environmentVariables(project)
    }
}
