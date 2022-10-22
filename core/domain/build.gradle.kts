plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kabos.domain"
    compileSdk = 33
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.coroutines)

    testImplementation(libs.bundles.test.base)
}
