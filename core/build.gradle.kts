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
    implementation(Dependencies.kotlin)
    implementation(Dependencies.material)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.recyclerview)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.preference)
    implementation(Dependencies.navigation)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.fragment)

    implementation(Dependencies.koin)
    implementation(Dependencies.koinScope)
    implementation(Dependencies.koinViewModel)
    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxAndroid)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.retrofitRxJava)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomRxJava)
    implementation(Dependencies.lifecycleExtensions)
    kapt(Dependencies.lifecycleCompiler)
    implementation(Dependencies.lifecycleReactive)
    implementation(Dependencies.lifecycleLiveData)

    detektPlugins(Dependencies.detekt)
    implementation(Dependencies.stetho)
    implementation(Dependencies.stethoOkHttp)

    Dependencies.testImplementations.forEach(::testImplementation)
}
