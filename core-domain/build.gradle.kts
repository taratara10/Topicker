import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("android")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.kabos.topicker.core.domain"
    compileSdk = 33
}

dependencies {
    implementation(project(":core-model"))

    implementation(libs.coroutines)

    testImplementation(libs.bundles.test.base)


    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
}
