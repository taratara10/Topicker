plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.kabos.topicker.core.datastore"
    compileSdk = 33
}

dependencies {
    testImplementation(libs.test.junit)

    implementation(libs.datastore)
}
