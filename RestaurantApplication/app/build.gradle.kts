plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.restaurantapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.restaurantapplication"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.lottie)
    implementation (libs.material)

    implementation(libs.retrofit)
    //noinspection UseTomlInstead
    implementation(libs.converter.gson)

    implementation (libs.material.v1110)


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}