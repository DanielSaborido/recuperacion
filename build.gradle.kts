import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") version "1.8.10"
    id("org.jetbrains.compose") version "1.4.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://mvnrepository.com/artifact/com.h2database/h2")
    maven("https://mvnrepository.com/artifact/de.m3y.kformat/kformat/0.9")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.h2database:h2:1.4.200")
                implementation("de.m3y.kformat:kformat:0.9")
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "recuperacion"
            packageVersion = "1.0.0"
        }
    }
}
