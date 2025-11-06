rootProject.name = "Android Compose Base"
include("app")
include(":core:data", ":core:domain", ":core:usecases", ":core:di")

pluginManagement { // https://kotlinlang.org/docs/whatsnew1820.html#configure-gradle-settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
