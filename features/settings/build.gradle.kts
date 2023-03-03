@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.carles.settings"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        //testInstrumentationRunner = AppConfig.testRunner
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("main").java.srcDirs(File("$buildDir/generated/source/kapt/main"))
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
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
    implementation(project(":common"))
    implementation(Dependence.kotlin)
    implementation(Dependence.appCompat)
    implementation(Dependence.core)
    implementation(Dependence.material)
    implementation(Dependence.constraintLayout)
    implementation(Dependence.preference)
    implementation(Dependence.navigation)
    implementation(Dependence.navigationFragment)
    implementation(Dependence.koin)
    implementation(Dependence.koinNavigation)
    implementation(Dependence.rxJava)
    implementation(Dependence.rxAndroid)

    detektPlugins(Dependence.detekt)

    Dependence.testImplementations.forEach(::testImplementation)
}
