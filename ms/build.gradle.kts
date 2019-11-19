import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlinx-serialization") version "1.3.50"
    kotlin("kapt")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "io.github.diov.msaider"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.languageVersion = "1.4"
    }
}

dependencies {
    val appCenterSdkVersion = "2.3.0"

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation("androidx.core:core-ktx:1.2.0-beta02")
    implementation("com.google.android.material:material:1.2.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0-rc02")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.squareup.okhttp3:okhttp:4.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.0")
    implementation("io.coil-kt:coil:0.8.0")
    implementation("com.microsoft.appcenter:appcenter-analytics:${appCenterSdkVersion}")
    implementation("com.microsoft.appcenter:appcenter-crashes:${appCenterSdkVersion}")

    debugImplementation("com.facebook.flipper:flipper:0.25.0")
    debugImplementation("com.facebook.flipper:flipper-network-plugin:0.25.0")
    debugImplementation("com.facebook.soloader:soloader:0.8.0")
    releaseImplementation("com.facebook.flipper:flipper-noop:0.25.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.3.0-alpha02")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0-alpha02")
}
