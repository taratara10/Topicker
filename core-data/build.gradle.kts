plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.kabos.topicker.core.data"
    compileSdk = 33

}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-domain"))
    implementation(project(":core-datastore"))

    testImplementation(libs.bundles.test.base)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation(libs.timber)

    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
}
