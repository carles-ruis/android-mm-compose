// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }
   dependencies {
         classpath("com.android.tools.build:gradle:${Version.gradle}")
         classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
    }
}

plugins {
    // use: ./gradlew dependencyUpdates
    id("com.github.ben-manes.versions").version(Version.benManes)
    // ./gradlew detekt
    id("io.gitlab.arturbosch.detekt").version(Version.detekt)
}

allprojects {
    repositories {
        google()
        gradlePluginPortal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
