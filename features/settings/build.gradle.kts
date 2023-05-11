@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.carles.settings"
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
    implementation(project(":common"))
    implementation(Dependence.kotlin)
    implementation(Dependence.appCompat)
    implementation(Dependence.core)
    implementation(Dependence.hilt)
    kapt(Dependence.hiltCompiler)

    implementation(platform(Dependence.composeBom))
    implementation(Dependence.material3)
    implementation(Dependence.lifecycleViewModel)
    implementation(Dependence.lifecycleRuntime)

    detektPlugins(Dependence.detekt)
    testImplementation(project(":commontest"))
    TestDependence.testImplementations.forEach(::testImplementation)
}
