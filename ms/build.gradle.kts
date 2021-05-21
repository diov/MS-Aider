import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlinx-serialization") version "1.3.50"
    kotlin("kapt")
}

val keystoreProperties = Properties()
val keystorePropertiesFile = File(rootDir, "keystore.properties")
if (keystorePropertiesFile.canRead()) {
    keystoreProperties.load(keystorePropertiesFile.inputStream())
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "io.github.diov.msaider"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("release.keystore")
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storePassword = keystoreProperties["storePassword"] as String
        }
        create("release") {
            storeFile = rootProject.file("release.keystore")
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.languageVersion = "1.4"
    }
}

dependencies {
    val appCenterSdkVersion = "4.1.1"

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation("androidx.core:core-ktx:1.6.0-beta01")
    implementation("com.google.android.material:material:1.4.0-beta01")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.browser:browser:1.3.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc")
    implementation("com.microsoft.appcenter:appcenter-analytics:${appCenterSdkVersion}")
    implementation("com.microsoft.appcenter:appcenter-crashes:${appCenterSdkVersion}")

    debugImplementation("com.facebook.flipper:flipper:0.87.0")
    debugImplementation("com.facebook.flipper:flipper-network-plugin:0.87.0")
    debugImplementation("com.facebook.soloader:soloader:0.10.1")
    releaseImplementation("com.facebook.flipper:flipper-noop:0.87.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0-beta01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0-beta01")
}
