plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.kabos.topicker.core.design"
    compileSdk = 33

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    debugImplementation(composeBom)

    debugImplementation(libs.bundles.debug.compose)

    implementation(libs.bundles.androidx.base)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.timber)

    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
}
