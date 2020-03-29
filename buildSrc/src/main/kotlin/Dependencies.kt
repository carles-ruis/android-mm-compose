object Versions {
    val kotlin = "1.3.70"
    val appCompat = "1.1.0"
    val material = "1.1.0-alpha09"
    val recyclerView = "1.1.0"
    val constraintLayout = "1.1.3"
    val preference = "1.1.0"
    val retrofit = "2.5.0"
    val rxJava = "2.2.17"
    val rxAndroid = "2.1.1"
    val lifecycle = "2.2.0"
    val room = "2.2.3"
    val koin = "2.1.0-alpha-10"
    val stetho = "1.5.1"
    val leakCanary = "2.2"
    val gson = "2.8.2"

    val androidTest = "1.1.1"
    val mockk = "1.9.3"
    val archCoreTesting = "2.1.0"
    val espresso = "3.2.0"
    val jUnit = "4.13"
    val assertj = "3.14.0"
}

object Dependencies {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val material = "com.google.android.material:material:${Versions.material}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val preference = "androidx.preference:preference-ktx:${Versions.preference}"

    val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val koin = "org.koin:koin-android:${Versions.koin}"
    val koinScope = "org.koin:koin-android-scope:${Versions.koin}"
    val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomRxJava = "androidx.room:room-rxjava2:${Versions.room}"
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    val lifecycleReactive = "androidx.lifecycle:lifecycle-reactivestreams:${Versions.lifecycle}"
    val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"

    val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    object Test {
        val jUnit = "junit:junit:${Versions.jUnit}"
        val mockk = "io.mockk:mockk:${Versions.mockk}"
        val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
        val assertj = "org.assertj:assertj-core:${Versions.assertj}"

        val testRunner = "androidx.test.ext:junit:${Versions.androidTest}"
        val testRules = "androidx.test:rules:${Versions.androidTest}"
        val espressoContrib = "com.android.support.test.espresso:espresso-contrib:${Versions.espresso}"
        val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    }
}