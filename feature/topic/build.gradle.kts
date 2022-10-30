plugins {
    kotlin("android")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.kabos.topicker.feature.topic"
    compileSdk = 33

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core-domain"))
    implementation(project(":core-design"))

    debugImplementation(libs.bundles.debug.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.base)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.timber)

    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
}
