plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.justbaat.myapplicationKotlin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.justbaat.myapplicationKotlin"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.justbaatAds:adsSdk:1.0.6")
    implementation("com.unity3d.ads-mediation:mediation-sdk:8.9.1")
    implementation("com.google.android.gms:play-services-appset:16.0.0")
    implementation ("com.google.android.gms:play-services-ads-identifier:18.1.0")
    implementation ("com.google.android.gms:play-services-basement:18.1.0")
    implementation("com.google.android.gms:play-services-ads:24.4.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}