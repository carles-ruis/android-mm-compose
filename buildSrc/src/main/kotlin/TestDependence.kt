object TestDependence {
    val junit = "junit:junit:4.13.2"
    val testRunner = "androidx.test.ext:junit:1.1.5"
    val testRules = "androidx.test:rules:1.5.0"
    val hilt = "com.google.dagger:hilt-android-testing:${Version.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
    val composeUi = "androidx.compose.ui:ui-test-junit4"
    val composeUiManifest = "androidx.compose.ui:ui-test-manifest"
    val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1"

    private val mockk = "io.mockk:mockk:1.9.3"
    val testImplementations = listOf(junit, mockk, coroutinesTest)
}