val taskName = "explodeWebjars"
val webjars by configurations.creating

tasks.register<Copy>(taskName) {
    notCompatibleWithConfigurationCache("Uses the DefaultResolvedArtifact class, which is not Serializable")

    description = "Explodes WebJars to the build resources."
    group = "build"

    webjars.forEach { jar ->
        from(zipTree(jar)) {
            val config = webjars.resolvedConfiguration
            val artifact = config.resolvedArtifacts.find {
                "${it.file}" == jar.absolutePath
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

tasks.withType(ProcessResources::class.java).configureEach {
    dependsOn(":$taskName")
}
