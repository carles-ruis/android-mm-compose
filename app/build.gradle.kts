@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.dagger.hilt.android")
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
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtension
    }
    kapt {
        correctErrorTypes = true
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
    implementation(Dependence.hilt)
    kapt(Dependence.hiltCompiler)
    implementation(Dependence.retrofit)
    implementation(Dependence.retrofitConverterGson)
    implementation(Dependence.roomRuntime)

    val composeBom = platform(Dependence.composeBom)
    implementation(composeBom)
    implementation(Dependence.material3)
    implementation(Dependence.activityCompose)
    implementation(Dependence.navigation)
    implementation(Dependence.navigationAnimation)
    implementation(Dependence.lifecycleViewModel)
    implementation(Dependence.lifecycleRuntime)

    detektPlugins(Dependence.detekt)
    debugImplementation(Dependence.stetho)
    //debugImplementation(Dependence.leakCanary)

    TestDependence.testImplementations.forEach(::testImplementation)
    androidTestImplementation(TestDependence.junit)
    androidTestImplementation(TestDependence.testRunner)
    androidTestImplementation(TestDependence.testRules)
    androidTestImplementation(Dependence.gson)
    androidTestImplementation(Dependence.roomRuntime)
    androidTestImplementation(Dependence.roomKtx)
    kaptAndroidTest(Dependence.roomCompiler)
    androidTestImplementation(TestDependence.hilt)
    kaptAndroidTest(TestDependence.hiltCompiler)
    androidTestImplementation(composeBom)
    androidTestImplementation(TestDependence.composeUi)
    debugImplementation(TestDependence.composeUiManifest)
}
