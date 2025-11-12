import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    kotlin("jvm") version "2.2.21"
    id("com.gradleup.shadow") version "9.2.2"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "dev.tarna"
version = "0.4.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://repo.skriptlang.org/releases")
    maven("https://repo.destroystokyo.com/repository/maven-public")
    maven("https://repo.lunarclient.dev")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
    compileOnly("com.github.SkriptLang:Skript:2.13.1")
    compileOnly("com.lunarclient:apollo-api:1.2.0")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

kotlin {
    jvmToolchain(21)
}

tasks {
    shadowJar {
        relocate("org.bstats", "dev.tarna.sklunar.bstats")
    }

    processResources {
        filesNotMatching("**/*.png") {
            filter<ReplaceTokens>("tokens" to mapOf("version" to project.version))
        }
    }

    runServer {
        minecraftVersion("1.21.10")
    }
}