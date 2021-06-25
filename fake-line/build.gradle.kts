plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "jp.naver.line.android"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
