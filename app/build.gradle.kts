plugins {
    // Android Application
    alias(libs.plugins.android.application)
    // Google Devtools KSP
    alias(libs.plugins.google.devtools.ksp)
    // Dagger Hilt
    alias(libs.plugins.google.dagger.hilt.android)
    // Kotlin Android
    alias(libs.plugins.kotlin.android)
    // Kotlin Compose Compiler
    alias(libs.plugins.kotlin.compose.compiler)
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
        androidResources { localeFilters += setOf("en", "in") }
        vectorDrawables { useSupportLibrary = true }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            type = "String",
            name = "APP_LOCAL_DATASTORE_FILENAME",
            value = "\"app_local_datastore\""
        )
        buildConfigField(
            type = "String",
            name = "APP_LOCAL_DATABASE_FILENAME",
            value = "\"app_local_database\""
        )
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures{
        compose = true
        buildConfig = true
        shaders = false
    }
    testOptions.unitTests{ isIncludeAndroidResources = true }
}

dependencies {
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.adaptive)
    implementation(libs.androidx.material3.adaptive.navigation.suite)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.argon2)
    implementation(libs.bcprov.jdk15to18)
    implementation(libs.coil.compose)
    implementation(libs.google.dagger.hilt.android)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    ksp(libs.androidx.hilt.compiler)
    ksp(libs.androidx.room.compiler)
    ksp(libs.google.dagger.hilt.android.compiler)
}