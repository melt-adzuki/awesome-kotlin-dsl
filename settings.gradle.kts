rootProject.name = "awesome-kotlin-dsl"

plugins {
    // Automates download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

File(rootDir, "src").listFiles().forEach {
    include(":${it.name}")
    project(":${it.name}").projectDir = it
}
