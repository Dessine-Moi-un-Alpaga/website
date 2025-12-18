import be.alpago.environmentVariables

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
