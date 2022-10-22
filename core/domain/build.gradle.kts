plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kabos.domain"
    compileSdk = 33
}

dependencies {
    implementation(project(":core:model"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    testImplementation ("junit:junit:4.13.2")
}
