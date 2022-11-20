plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
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
