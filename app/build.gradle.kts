plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kabos.topicker"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions.add("appType")
    productFlavors {
        create("_mock") {
            dimension = "appType"
            versionNameSuffix = "-mock"
        }
        create("_dev") {
            dimension = "appType"
            versionNameSuffix = "-dev"
        }
        create("_prd") {
            dimension = "appType"
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:datastore"))

    testImplementation(libs.bundles.test.base)
    androidTestImplementation(libs.bundles.test.android)
    debugImplementation(libs.bundles.debug.compose)

    implementation(libs.bundles.androidx.base)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.compose.lottie)
    implementation(libs.timber)

    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(libs.datastore)

}
