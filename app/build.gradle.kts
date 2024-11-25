plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")

    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}

android {
    namespace = "com.gwabs.prodoctexplorerxml"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gwabs.prodoctexplorerxml"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)


    // Retrofit and Moshi for network calls
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.moshi.kotlin)


    // Kotlin Coroutines for background tasks
    implementation (libs.kotlinx.coroutines.android)

    // Hilt for Dependency Injection
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    // Glide for Image Loading
    implementation (libs.glide)
    
    // Unit Testing
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit.v115)
    androidTestImplementation (libs.androidx.espresso.core.v350)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // Coroutines
    implementation (libs.kotlinx.coroutines.android)

    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    //noinspection KaptUsageInsteadOfKsp
    kapt (libs.androidx.room.compiler)

    testImplementation (libs.junit)
    testImplementation (libs.mockito.core)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.androidx.core.testing)

    testImplementation ("junit:junit:4.13.2")

    testImplementation ("org.mockito:mockito-core:4.8.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")


}
