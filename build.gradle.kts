import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
}

group = "com.paulmethfessel"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}