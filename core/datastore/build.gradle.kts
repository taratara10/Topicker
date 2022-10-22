plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.kabos.datastore"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.bundles.test.android)

    implementation(libs.datastore)
}
