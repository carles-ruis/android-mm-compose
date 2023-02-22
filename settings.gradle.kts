include("app", "poi", "common", "settings")
rootProject.buildFileName = "build.gradle.kts"

project(":poi").projectDir = File(rootDir, "features/poi")
project(":settings").projectDir = File(rootDir, "features/settings")