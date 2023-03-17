object TestDependence {
    private val jUnit = "junit:junit:4.13.2"
    private val mockk = "io.mockk:mockk:1.9.3"
    private val archCoreTesting = "androidx.arch.core:core-testing:2.2.0-rc01"
    private val testRunner = "androidx.test.ext:junit:1.1.5}"
    private val testRules = "androidx.test:rules:1.5.0"

    val hilt = "com.google.dagger:hilt-android-testing:${Version.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
    val composeUi = "androidx.compose.ui:ui-test-junit4"
    val composeUiManifest = "androidx.compose.ui:ui-test-manifest"

    val testImplementations = listOf(jUnit, mockk, archCoreTesting)
    val androidTestImplementations = listOf(jUnit, archCoreTesting, testRunner, testRules)
}