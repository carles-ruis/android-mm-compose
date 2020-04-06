plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdkVersion(Configs.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Configs.minSdkVersion)
        targetSdkVersion(Configs.targetSdkVersion)
        versionCode = Configs.versionCode
        versionName = Configs.versionName
        testInstrumentationRunner = Configs.testRunner
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
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

androidExtensions {
    isExperimental = true
}

detekt {
    config = files("$rootDir/default-detekt-config.yml")
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.core)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.preference)
    implementation(Dependencies.navigation)
    implementation(Dependencies.navigationFragment)

    implementation(Dependencies.koin)
    implementation(Dependencies.koinScope)
    implementation(Dependencies.koinViewModel)
    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxAndroid)

    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.assertj)
}
