include("app", "hyrule", "common", "settings", "commontest")
rootProject.buildFileName = "build.gradle.kts"

project(":settings").projectDir = File(rootDir, "features/settings")
project(":hyrule").projectDir = File(rootDir, "features/hyrule")
