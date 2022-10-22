plugins {
    kotlin("android")
    id("com.android.library")
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

    implementation(libs.bundles.androidx.base)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.compose.lottie)
}
