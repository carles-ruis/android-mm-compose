// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }

    // ./gradlew detekt
//    plugins.apply("io.gitlab.arturbosch.detekt:1.6.0")
    // use: ./gradlew dependencyUpdates
    //  plugins.apply("com.github.ben-manes.versions:0.27.0")
}


tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
