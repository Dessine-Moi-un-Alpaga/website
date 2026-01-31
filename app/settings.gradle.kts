plugins {
    id("com.gradle.develocity") version("4.3.1")
}

rootProject.name = "website"

develocity {
    buildScan {
        publishing.onlyIf { false }

        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
    }
}
