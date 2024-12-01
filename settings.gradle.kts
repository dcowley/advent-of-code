dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

plugins {
    // Use the Foojay Toolchains Plugin to automatically download JDKs required by subprojects
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(":app")

rootProject.name = "advent-of-code"
