import org.gradle.api.file.RelativePath
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.register

val webjars by configurations.creating

tasks.register<Copy>("explodeWebjars") {
    description = "Explodes WebJars to the build resources."
    group = "build"

    webjars.forEach { jar ->
        from(zipTree(jar)) {
            val config = webjars.resolvedConfiguration
            val artifact = config.resolvedArtifacts.find {
                it.file.toString() == jar.absolutePath
            }
            exclude("META-INF/maven/**")
            exclude("META-INF/MANIFEST.MF")
            eachFile {
                val segments = relativePath.segments.drop(5).toTypedArray()
                relativePath = RelativePath(true, artifact!!.name, *segments)
            }
        }
    }
    into("${project.layout.buildDirectory.asFile.get()}/resources/main/static/webjars")
}

tasks.named("processResources").configure {
    dependsOn(":explodeWebjars")
}
