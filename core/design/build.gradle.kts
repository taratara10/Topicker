plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.kabos.topicker.core.design"
    compileSdk = 33
}

dependencies {

    implementation(libs.bundles.androidx.compose)
    implementation(libs.compose.lottie)
}
