import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    signingConfigs {
        create("releaseConfig") {
            storeFile =
                file("C:\\Users\\super\\AndroidStudioProjects\\BasicWallet\\my-release-key.jks")
            storePassword = "password"
            keyAlias = "my-key-alias"
            keyPassword = "password"
            enableV1Signing = true
            enableV2Signing = false
        }
    }

    namespace = "com.example.basicwallet"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.basicwallet"
        minSdk = 24
        targetSdk = 33
        versionCode = 7
        versionName = "1.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val buildDate = dateFormat.format(Date())
        buildConfigField("String", "BUILD_DATE", "\"$buildDate\"")
    }

    flavorDimensions.add("targetSdk")
    productFlavors {
        create("development") {
            dimension = "targetSdk"
            targetSdk = 33
        }
        create("clover") {
            dimension = "targetSdk"
            targetSdk = 29
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("releaseConfig")
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        baseline = file("lint-baseline.xml")
        checkReleaseBuilds = false
        abortOnError = false
    }
}

    dependencies {
        implementation(platform("androidx.compose:compose-bom:2024.06.00"))
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
        implementation("androidx.activity:activity-compose:1.9.0")
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.compose.ui:ui-test-junit4:1.0.1")
        implementation("androidx.databinding:databinding-runtime:8.5.1")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx")
        implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
        implementation("androidx.compose.runtime:runtime-livedata")
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("androidx.work:work-runtime-ktx:2.9.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("com.clover.sdk:clover-android-sdk:306")
        implementation("com.clover.sdk:clover-android-loyalty-kit:306")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:okhttp:4.9.3")
        implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
        implementation("com.google.code.gson:gson:2.10.1")
        implementation("com.jakewharton.timber:timber:4.7.1")

        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")

        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
    }
