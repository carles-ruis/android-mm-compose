@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.carles.common"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
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
    implementation(Dependence.kotlin)
    implementation(Dependence.appCompat)
    implementation(Dependence.datastore)
    implementation(Dependence.hilt)
    kapt(Dependence.hiltCompiler)
    implementation(Dependence.retrofit)
    implementation(Dependence.retrofitConverterGson)
    implementation(Dependence.roomRuntime)
    implementation(Dependence.roomKtx)
    kapt(Dependence.roomCompiler)

    implementation(platform(Dependence.composeBom))
    implementation(Dependence.material3)
    implementation(Dependence.navigation)
    implementation(Dependence.navigationAnimation)

    debugImplementation(Dependence.stethoOkHttp)
    debugImplementation(Dependence.loggingInterceptor)
    debugImplementation(Dependence.chucker)
    detektPlugins(Dependence.detekt)

    TestDependence.testImplementations.forEach(::testImplementation)
}
