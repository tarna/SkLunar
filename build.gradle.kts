plugins {
    kotlin("jvm") version "2.0.20"
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "dev.tarna"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://repo.skriptlang.org/releases")
    maven("https://repo.destroystokyo.com/repository/maven-public")
    maven("https://repo.lunarclient.dev")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("com.github.SkriptLang:Skript:2.9.4")
    compileOnly("com.lunarclient:apollo-api:1.1.6")
}

kotlin {
    jvmToolchain(21)
}

tasks {
    runServer {
        minecraftVersion("1.21.3")
    }
}