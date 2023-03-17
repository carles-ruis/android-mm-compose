object Dependence {
    private val retrofitVersion = "2.9.0"
    private val lifecycleVersion = "2.6.1"
    private val roomVersion = "2.5.0"
    private val stethoVersion = "1.6.0"

    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
    val appCompat = "androidx.appcompat:appcompat:1.7.0-alpha02"
    val core = "androidx.core:core:1.9.0"
    val preference = "androidx.preference:preference-ktx:1.2.0"
    val rxJava = "io.reactivex.rxjava2:rxjava:2.2.21"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.10.0"
    val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    val roomRxJava = "androidx.room:room-rxjava2:$roomVersion"
    val gson = "com.google.code.gson:gson:2.8.5"
    val hilt = "com.google.dagger:hilt-android:${Version.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.hilt}"

    // Http inspector
    val chucker = "com.github.chuckerteam.chucker:library:3.5.2"

    // Displays network traffic in chrome browser
    val stetho = "com.facebook.stetho:stetho:$stethoVersion"
    val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:$stethoVersion"

    // Memory leak detection library
    val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.10"
    val detekt = "io.gitlab.arturbosch.detekt:detekt-formatting:${Version.detekt}"

    // compose
    val composeBom = "androidx.compose:compose-bom:2023.01.00"
    val material3 = "androidx.compose.material3:material3"
    val activityCompose = "androidx.activity:activity-compose"
    val navigation = "androidx.hilt:hilt-navigation-compose:1.1.0-alpha01"
    val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:0.30.0"
    val coil = "io.coil-kt:coil-compose:2.2.2"
    val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
}

