buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
        mavenCentral();
    }
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.currency"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.currency"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")


    val lifecycle_version = "2.7.0"
    val arch_version = "2.2.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
      // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.0.2")




}