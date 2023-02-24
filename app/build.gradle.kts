@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.carles.mm"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.carles.carleskotlin"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.testRunner
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("main").java.srcDirs(File("$buildDir/generated/source/kapt/main"))
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
        getByName("androidTest").java.srcDirs(File("$buildDir/generated/source/kapt/main"))
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

detekt {
    config = files("$rootDir/default-detekt-config.yml")
}

dependencies {
    implementation(project(":hyrule"))
    implementation(project(":settings"))
    implementation(project(":common"))
    implementation(Dependence.kotlin)
    implementation(Dependence.appCompat)
    implementation(Dependence.constraintLayout)
    implementation(Dependence.navigation)
    implementation(Dependence.navigationFragment)
    implementation(Dependence.fragment)
    implementation(Dependence.koin)
    implementation(Dependence.koinNavigation)

    implementation(Dependence.stetho)
    implementation(Dependence.stethoOkHttp)
    debugImplementation(Dependence.leakCanary)
    detektPlugins(Dependence.detekt)

    Dependence.testImplementations.forEach(::testImplementation)
    Dependence.androidTestImplementations.forEach(::androidTestImplementation)
    androidTestImplementation(Dependence.rxJava)
    androidTestImplementation(Dependence.rxAndroid)
    androidTestImplementation(Dependence.gson)
    androidTestImplementation(Dependence.roomRuntime)
    kaptAndroidTest(Dependence.roomCompiler)
    androidTestImplementation(Dependence.roomRxJava)
}
