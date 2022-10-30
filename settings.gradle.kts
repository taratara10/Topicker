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

    /**
     * project共通でライブラリのバージョンを管理する
     * */
    versionCatalogs {
        create("libs") {
            /**
             * version
             * */
            version("compose", "1.2.1")
            version("compose-bom", "2022.10.00")
            version("compose-pager", "0.26.1-alpha")
            version("compose-lifecycle", "2.6.0-alpha01")

            /** android core */
            alias("androidx-core").to("androidx.core:core-ktx:1.9.0")
            alias("androidx-lifecycle").to("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
            bundle("androidx-base", listOf("androidx-core", "androidx-lifecycle"))

            /** compose */
            alias("compose-bom")
                .to("androidx.compose", "compose-bom")
                .versionRef("compose-bom")
            alias("compose-material")
                .to("androidx.compose.material:material:1.3.0")

            alias("accompanist-pager")
                .to("com.google.accompanist", "accompanist-pager")
                .versionRef("compose-pager")

            alias("compose-activity").to("androidx.activity:activity-compose:1.6.0")
            alias("compose-navigation").to("androidx.navigation:navigation-compose:2.5.2")
            alias("compose-navigation-hilt").to("androidx.hilt:hilt-navigation-compose:1.0.0")
            alias("compose-viewmodel").to("androidx.lifecycle", "lifecycle-viewmodel-compose")
                .versionRef("compose-lifecycle")
            alias("compose-lifecycle").to("androidx.lifecycle", "lifecycle-runtime-compose")
                .versionRef("compose-lifecycle")
            alias("compose-lottie").to("com.airbnb.android:lottie-compose:5.2.0")
            bundle(
                "androidx.compose",
                listOf(
                    "compose-material",
                    "compose-activity",
                    "compose-navigation",
                    "compose-navigation-hilt",
                    "compose-viewmodel",
                    "compose-lifecycle",
                    "accompanist-pager",
                    "compose-lottie",
                )
            )

            /** DI */
            version("hilt", "2.42")
            alias("hilt-android")
                .to("com.google.dagger", "hilt-android")
                .versionRef("hilt")
            alias("hilt-kapt")
                .to("com.google.dagger", "hilt-android-compiler")
                .versionRef("hilt")


            /** firebase */
            alias("firebase-bom").to("com.google.firebase:firebase-bom:30.3.1")
            alias("firebase-firestore").to("com.google.firebase:firebase-firestore-ktx:24.4.0")
            alias("firebase-await").to("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

            /** datastore */
            alias("datastore").to("androidx.datastore:datastore-preferences:1.0.0")

            /** Timber */
            alias("timber").to("com.jakewharton.timber:timber:4.7.1")

            /** coroutines */
            alias("coroutines").to("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

            /** Test */
            alias("test-junit").to("junit:junit:4.13.2")
            alias("androidx-test-junit").to("androidx.test.ext:junit:1.1.3")
            alias("androidx-test-compose")
                .to("androidx.compose.ui", "ui-test-junit4")
                .versionRef("compose")
            bundle("test-base", listOf("test-junit"))
            bundle("test-android", listOf("androidx-test-junit", "androidx-test-compose"))

            /** debug */
            alias("debug-compose-tooling")
                .to("androidx.compose.ui", "ui-tooling")
                .versionRef("compose")
            alias("debug-compose-manifest")
                .to("androidx.compose.ui", "ui-test-manifest")
                .versionRef("compose")
            bundle("debug-compose", listOf("debug-compose-tooling", "debug-compose-manifest"))

        }
    }
}

rootProject.name = "Topicker"
include(":app")
include(":core:model")
include(":core-data")
include(":core:domain")
include(":core-datastore")
include(":core-design")
include(":feature:topic")
include(":feature:collection")
