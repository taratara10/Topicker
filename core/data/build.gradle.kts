plugins {
    kotlin("android")
    id("com.android.library")
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

    testImplementation(libs.bundles.test.base)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.await)

    implementation(libs.timber)
}
