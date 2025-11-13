val taskName = "explodeWebjars"
val webjars by configurations.creating

val filters = mapOf(
    "escape-velocity" to setOf(
        "css/**",
        "js/**",
    ),
    "font-awesome" to setOf(
        "css/brands.min.css",
        "css/fontawesome.min.css",
        "css/solid.min.css",
        "webfonts/*",
    ),
    "jquery.dropotron" to setOf(
        "jquery.dropotron.min.js",
    ),
    "jquery" to setOf(
        "jquery.min.js",
    ),
    "photoswipe" to setOf(
        "dist/photoswipe.css",
        "dist/photoswipe.esm.min.js",
        "dist/photoswipe-lightbox.esm.min.js",
    ),
    "responsive-tools" to setOf(
        "dist/breakpoints.min.js",
        "dist/browser.min.js",
    ),
    "toastr" to setOf(
        "build/toastr.min.css",
        "build/toastr.min.js",
    )
)

tasks.register<Copy>(taskName) {
    description = "Explodes WebJars to the build resources."
    group = "build"

    webjars.forEach { jar ->
        from(zipTree(jar)) {
            val config = webjars.resolvedConfiguration
            val artifact = config.resolvedArtifacts.find {
                "${it.file}" == jar.absolutePath
            }

            val artifactName = artifact!!.name
            val artifactVersion = artifact.moduleVersion.id.version
            println("${artifactName}:${artifactVersion}")
            val filtersForArtifact = filters[artifactName] ?: emptySet()

            filtersForArtifact.forEach { filter ->
                include("META-INF/resources/webjars/${artifactName}/${artifactVersion}/${filter}")
            }
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
