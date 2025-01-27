plugins {
    // Android Application
    alias(libs.plugins.android.application)
    // Google Devtools KSP
    alias(libs.plugins.google.devtools.ksp)
    // Dagger Hilt
    alias(libs.plugins.google.dagger.hilt.android)
    // Kotlin Android
    alias(libs.plugins.kotlin.android)
    // Kotlin Serialization
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.thomas200593.mdm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.thomas200593.mdm"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        compose = true
        buildConfig = true
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.multidex)
    implementation(libs.google.dagger.hilt.android)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    ksp(libs.androidx.hilt.compiler)
    ksp(libs.google.dagger.hilt.android.compiler)
}