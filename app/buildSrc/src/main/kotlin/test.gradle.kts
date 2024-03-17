import be.alpago.environmentVariables
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    id("jacoco")
}

afterEvaluate {
    tasks.named<Test>("test").configure {
        dependsOn(":firestoreEmulator")
        useJUnitPlatform()
        environmentVariables(project)
    }

    tasks.named<JacocoReport>("jacocoTestReport").configure {
        reports {
            html.required = false
            xml.required = true
        }
    }
}
