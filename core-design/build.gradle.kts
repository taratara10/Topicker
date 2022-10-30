plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.kabos.topicker.core.design"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.base)
    implementation(libs.bundles.androidx.compose)
    debugImplementation(libs.bundles.debug.compose)
    testImplementation(libs.bundles.test.base)
}
