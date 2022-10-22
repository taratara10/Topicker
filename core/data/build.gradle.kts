plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kabos.data"
    compileSdk = 33

    flavorDimensions.add("appType")
    productFlavors {
        create("_mock") {
            dimension = "appType"
        }
        create("_dev") {
            dimension = "appType"
        }
        create("_prd") {
            dimension = "appType"
        }
    }

    with(sourceSets) {
        getByName("_mock") {
            java.srcDirs("src/mock/java")
        }
        getByName("_dev") {
            java.srcDirs("src/main/java")
        }
        getByName("_prd") {
            java.srcDirs("src/main/java")
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:datastore"))

    testImplementation("junit:junit:4.13.2")

    implementation(platform("com.google.firebase:firebase-bom:30.3.1"))
    implementation("com.google.firebase:firebase-firestore-ktx:24.4.0")

    implementation("com.jakewharton.timber:timber:4.7.1")

    // firebaseでawait()するやつ
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // dataStore
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
}
