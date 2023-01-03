plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
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
        versionCode = 2
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
//            isMinifyEnabled = true
//            isShrinkResources = true
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
        create("_dev") {
            dimension = "appType"
            versionNameSuffix = "-dev"
        }
        create("_prd") {
            dimension = "appType"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-data"))
    implementation(project(":core-domain"))
    implementation(project(":core-datastore"))
    implementation(project(":core-design"))

    implementation(project(":feature-topic"))
    implementation(project(":feature-collection"))
    implementation(project(":feature-setting"))

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    debugImplementation(composeBom)

    testImplementation(libs.bundles.test.base)
    androidTestImplementation(libs.bundles.test.android)
    debugImplementation(libs.bundles.debug.compose)

    implementation(libs.bundles.androidx.base)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.timber)

    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)

    implementation(libs.bundles.firebase)
}
