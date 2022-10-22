plugins {
    kotlin("android")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.kabos.topicker.core.datastore"
    compileSdk = 33
}

dependencies {
    testImplementation(libs.test.junit)

    implementation(libs.datastore)

    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
}
