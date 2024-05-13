plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
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
