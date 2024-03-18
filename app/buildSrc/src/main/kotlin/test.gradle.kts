import be.alpago.environmentVariables
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    id("jacoco")
}

afterEvaluate {
    tasks.withType(Test::class.java).configureEach {
        dependsOn(":firestoreEmulator")
        useJUnitPlatform()
        environmentVariables(project)
    }

    tasks.withType(JacocoReport::class.java).configureEach {
        reports {
            html.required = false
            xml.required = true
        }
    }
}
