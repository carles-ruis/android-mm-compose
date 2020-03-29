plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

//apply {
//  from("$rootDir/detekt.gradle")
//}

// android.applyDefaults(project)

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
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":poi"))
    implementation(project(":core"))

    implementation(Dependencies.kotlin)

    implementation(Dependencies.koin)
    implementation(Dependencies.koinScope)
    implementation(Dependencies.koinViewModel)

    implementation(Dependencies.stetho)
    implementation(Dependencies.stethoOkHttp)
    debugImplementation(Dependencies.leakCanary)
    //detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.6.0")

    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.archCoreTesting)
    testImplementation(Dependencies.Test.assertj)

    androidTestImplementation(Dependencies.Test.jUnit)
    androidTestImplementation(Dependencies.Test.testRules)
    androidTestImplementation(Dependencies.Test.testRunner)
    androidTestImplementation(Dependencies.Test.espresso)
    androidTestImplementation(Dependencies.Test.espressoContrib)
    androidTestImplementation(Dependencies.Test.archCoreTesting)
    androidTestImplementation(Dependencies.Test.assertj)
    androidTestImplementation(Dependencies.rxJava)
    androidTestImplementation(Dependencies.rxAndroid)
    androidTestImplementation(Dependencies.gson)
    androidTestImplementation(Dependencies.roomRuntime)
    kaptAndroidTest(Dependencies.roomCompiler)
    androidTestImplementation(Dependencies.roomRxJava)
}