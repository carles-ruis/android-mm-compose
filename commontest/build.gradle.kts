plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.carles.commontest"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
        kotlinOptions {
            jvmTarget = AppConfig.jvmTarget
        }
}

dependencies {
    implementation(TestDependence.junit)
    implementation(TestDependence.coroutinesTest)
}