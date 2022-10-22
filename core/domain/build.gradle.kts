plugins {
    kotlin("android")
    id("com.android.library")
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
