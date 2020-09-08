plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")

    implementation("io.ktor:ktor-client-cio:1.4.0")
    implementation("io.ktor:ktor-client-json:1.4.0")
    implementation("io.ktor:ktor-client-serialization:1.4.0")
}

