plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.4.30-RC"
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdkVersion(Configs.compileSdkVersion)

    defaultConfig {
        applicationId = "com.carles.carleskotlin"
        minSdkVersion(Configs.minSdkVersion)
        targetSdkVersion(Configs.targetSdkVersion)
        versionCode = Configs.versionCode
        versionName = Configs.versionName
        testInstrumentationRunner = Configs.testRunner

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
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
    implementation(project(":poi"))
    implementation(project(":settings"))
    implementation(project(":core"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.navigation)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.fragment)

    implementation(Dependencies.koin)
    implementation(Dependencies.koinScope)
    implementation(Dependencies.koinViewModel)

    implementation(Dependencies.stetho)
    implementation(Dependencies.stethoOkHttp)
    debugImplementation(Dependencies.leakCanary)
    debugImplementation(Dependencies.debugDb)
    detektPlugins(Dependencies.detekt)

    Dependencies.testImplementations.forEach(::testImplementation)
    Dependencies.androidTestImplementations.forEach(::androidTestImplementation)
    androidTestImplementation(Dependencies.rxJava)
    androidTestImplementation(Dependencies.rxAndroid)
    androidTestImplementation(Dependencies.gson)
    androidTestImplementation(Dependencies.roomRuntime)
    kaptAndroidTest(Dependencies.roomCompiler)
    androidTestImplementation(Dependencies.roomRxJava)
}