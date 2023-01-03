pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

}

/**
 * Gradle7.3.3ではfeaturePreviewなので宣言
 * Gradle7.5ではstableになるので、不要になる
 * https://docs.gradle.org/7.3.3/userguide/platforms.html
 * */
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Topicker"
include(":app")
include(":core-model")
include(":core-data")
include(":core-domain")
include(":core-datastore")
include(":core-design")
include(":feature-topic")
include(":feature-collection")
include(":feature-setting")
