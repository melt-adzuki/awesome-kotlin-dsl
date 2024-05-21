plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    application
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "application")

    sourceSets {
        main {
            kotlin {
                srcDir(".")
            }
        }
    }

    application {
        mainClass = "MainKt"
    }
}
