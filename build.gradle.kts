import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = "com.paulmethfessel"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
}

tasks {
    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.paulmethfessel.windowspong.GameKt"))
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}